import org.apache.commons.compress.compressors.gzip.GzipCompressorInputStream;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.LinkedList;

public class OnDemand implements Runnable {

    public final DoublyLinkedList pending = new DoublyLinkedList();
    public final byte[] buffer = new byte[500];
    public final byte[][] storeFilePriorities = new byte[4][];
    public final DoublyLinkedList prefetches = new DoublyLinkedList();
    public final DoublyLinkedList completed = new DoublyLinkedList();
    public final LinkedList<OnDemandRequest> requests = new LinkedList<>();
    public final int[][] storeFileVersions = new int[4][];
    public final int[][] storeFileChecksums = new int[4][];
    public final DoublyLinkedList missing = new DoublyLinkedList();
    public final DoublyLinkedList queue = new DoublyLinkedList();
    private final Object lock = new Object();
    public int totalPrefetchFiles;
    public int topPriority;
    public String message = "";
    public int heartbeatCycle;
    public long socketOpenTime;
    public int[] mapLocFile;
    public int cycle;
    public Game game;
    public int partOffset;
    public int partAvailable;
    public int[] midiIndex;
    public int failCount;
    public int[] mapLandFile;
    public int loadedPretechFiles;
    public boolean running = true;
    public OutputStream out;
    public int[] mapPrefetched;
    public boolean active = false;
    public int[] animIndex;
    public InputStream in;
    public Socket socket;
    public int importantCount;
    public int requestCount;
    public OnDemandRequest current;
    public int[] mapIndex;
    public byte[] modelIndex;
    public int waitCycles;

    public OnDemand() {
    }

    public boolean validate(int expectedVersion, int crc, byte[] src) {
        return true;
    }

    public void read() {
        try {
            int available = in.available();

            if ((partAvailable == 0) && (available >= 6)) {
                active = true;

                in.read(buffer, 0, 6);

                int store = buffer[0] & 0xff;
                int file = ((buffer[1] & 0xff) << 8) + (buffer[2] & 0xff);
                int size = ((buffer[3] & 0xff) << 8) + (buffer[4] & 0xff);
                int part = buffer[5] & 0xff;

                current = null;

                for (OnDemandRequest request = (OnDemandRequest) pending.peekFront(); request != null; request = (OnDemandRequest) pending.prev()) {
                    if ((request.store == store) && (request.file == file)) {
                        current = request;
                    }
                    if (current != null) {
                        request.cycle = 0;
                    }
                }

                if (current != null) {
                    waitCycles = 0;

                    if (size == 0) {
                        Signlink.reporterror("Rej: " + store + "," + file);

                        current.data = null;

                        if (current.important) {
                            synchronized (completed) {
                                completed.pushBack(current);
                            }
                        } else {
                            current.unlink();
                        }

                        current = null;
                    } else {
                        if ((current.data == null) && (part == 0)) {
                            current.data = new byte[size];
                        }

                        if (current.data == null) {
                            throw new IOException("missing start of file");
                        }
                    }
                }

                partOffset = part * 500;
                partAvailable = 500;

                if (partAvailable > (size - (part * 500))) {
                    partAvailable = size - (part * 500);
                }
            }

            if ((partAvailable > 0) && (available >= partAvailable)) {
                active = true;
                byte[] dst = buffer;
                int offset = 0;

                if (current != null) {
                    dst = current.data;
                    offset = partOffset;
                }

                in.read(dst, offset, partAvailable);

                if (((partAvailable + partOffset) >= dst.length) && (current != null)) {
                    if (game.filestores[0] != null) {
                        game.filestores[current.store + 1].write(dst, current.file, dst.length);
                    }

                    if (!current.important && (current.store == 3)) {
                        current.important = true;
                        current.store = 93;
                    }

                    if (current.important) {
                        synchronized (completed) {
                            completed.pushBack(current);
                        }
                    } else {
                        current.unlink();
                    }
                }
                partAvailable = 0;
            }
        } catch (IOException ioexception) {
            try {
                socket.close();
            } catch (Exception ignored) {
            }
            socket = null;
            in = null;
            out = null;
            partAvailable = 0;
        }
    }

    public void load(FileArchive versionlist, Game game) throws IOException {
        String[] versionFilenames = {"model_version", "anim_version", "midi_version", "map_version"};

        for (int i = 0; i < 4; i++) {
            byte[] data = versionlist.read(versionFilenames[i]);
            int count = data.length / 2;

            Buffer buffer = new Buffer(data);

            storeFileVersions[i] = new int[count];
            storeFilePriorities[i] = new byte[count];

            for (int l = 0; l < count; l++) {
                storeFileVersions[i][l] = buffer.readU16();
            }
        }

        String[] crcFilenames = {"model_crc", "anim_crc", "midi_crc", "map_crc"};

        for (int i = 0; i < 4; i++) {
            byte[] data = versionlist.read(crcFilenames[i]);
            int count = data.length / 4;
            Buffer buffer = new Buffer(data);
            storeFileChecksums[i] = new int[count];
            for (int l1 = 0; l1 < count; l1++) {
                storeFileChecksums[i][l1] = buffer.read32();
            }
        }

        byte[] data = versionlist.read("model_index");
        int count = storeFileVersions[0].length;

        modelIndex = new byte[count];

        for (int i = 0; i < count; i++) {
            if (i < data.length) {
                modelIndex[i] = data[i];
            } else {
                modelIndex[i] = 0;
            }
        }

        data = versionlist.read("map_index");
        Buffer buffer = new Buffer(data);
        count = data.length / 7;

        mapIndex = new int[count];
        mapLandFile = new int[count];
        mapLocFile = new int[count];
        mapPrefetched = new int[count];

        for (int i2 = 0; i2 < count; i2++) {
            mapIndex[i2] = buffer.readU16();
            mapLandFile[i2] = buffer.readU16();
            mapLocFile[i2] = buffer.readU16();
            mapPrefetched[i2] = buffer.readU8();
        }

        data = versionlist.read("anim_index");
        buffer = new Buffer(data);
        count = data.length / 2;
        animIndex = new int[count];

        for (int j2 = 0; j2 < count; j2++) {
            animIndex[j2] = buffer.readU16();
        }

        data = versionlist.read("midi_index");
        buffer = new Buffer(data);
        count = data.length;
        midiIndex = new int[count];

        for (int k2 = 0; k2 < count; k2++) {
            midiIndex[k2] = buffer.readU8();
        }

        this.game = game;
        running = true;
        this.game.startThread(this, 2);
    }

    public int remaining() {
        synchronized (lock) {
            return requests.size();
        }
    }

    public void stop() {
        running = false;
    }

    public void prefetchMaps(boolean members) {
        int count = mapIndex.length;
        for (int i = 0; i < count; i++) {
            if (members || (mapPrefetched[i] != 0)) {
                prefetch((byte) 2, 3, mapLocFile[i]);
                prefetch((byte) 2, 3, mapLandFile[i]);
            }
        }
    }

    public int getFileCount(int store) {
        return storeFileVersions[store].length;
    }

    public void send(OnDemandRequest request) {
        try {
            if (socket == null) {
                long now = System.currentTimeMillis();
                if ((now - socketOpenTime) < 4000L) {
                    return;
                }
                socketOpenTime = now;
                socket = game.openSocket(43594 + Game.portOffset);
                in = socket.getInputStream();
                out = socket.getOutputStream();
                out.write(15);
                for (int j = 0; j < 8; j++) {
                    in.read();
                }
                waitCycles = 0;
            }

            buffer[0] = (byte) request.store;
            buffer[1] = (byte) (request.file >> 8);
            buffer[2] = (byte) request.file;

            if (request.important) {
                buffer[3] = 2;
            } else if (!game.ingame) {
                buffer[3] = 1;
            } else {
                buffer[3] = 0;
            }

            out.write(buffer, 0, 4);
            heartbeatCycle = 0;
            failCount = -10000;
            return;
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            socket.close();
        } catch (Exception ignored) {
        }
        socket = null;
        in = null;
        out = null;
        partAvailable = 0;
        failCount++;
    }

    public int getAnimationFrameCount() {
        return animIndex.length;
    }

    public void request(int store, int file) {
        // block music
        if (store == 2) {
            return;
        }
        if ((store < 0) || (store > storeFileVersions.length) || (file < 0) || (file > storeFileVersions[store].length)) {
            return;
        }
        if (storeFileVersions[store][file] == 0) {
            return;
        }

        synchronized (lock) {
            for (OnDemandRequest request : requests) {
                if ((request.store == store) && (request.file == file)) {
                    return;
                }
            }
            OnDemandRequest request = new OnDemandRequest();
            request.store = store;
            request.file = file;
            request.important = true;
            synchronized (queue) {
                queue.pushBack(request);
            }
            requests.addFirst(request);
        }
    }

    public int getModelFlags(int id) {
        return modelIndex[id] & 0xff;
    }

    @Override
    public void run() {
        try {
            while (running) {
                cycle++;

                int del = 20;

                if ((topPriority == 0) && (game.filestores[0] != null)) {
                    del = 50;
                }

                try {
                    Thread.sleep(del);
                } catch (Exception ignored) {
                }

                active = true;

                for (int j = 0; j < 100; j++) {
                    if (!active) {
                        break;
                    }

                    active = false;

                    handleQueue();
                    handlePending();

                    if ((importantCount == 0) && (j >= 5)) {
                        break;
                    }

                    handleExtras();

                    if (in != null) {
                        read();
                    }
                }

                boolean loading = false;

                for (OnDemandRequest request = (OnDemandRequest) pending.peekFront(); request != null; request = (OnDemandRequest) pending.prev()) {
                    if (request.important) {
                        loading = true;
                        request.cycle++;

                        if (request.cycle > 50) {
                            request.cycle = 0;
                            send(request);
                        }
                    }
                }

                if (!loading) {
                    for (OnDemandRequest request = (OnDemandRequest) pending.peekFront(); request != null; request = (OnDemandRequest) pending.prev()) {
                        loading = true;
                        request.cycle++;

                        if (request.cycle > 50) {
                            request.cycle = 0;
                            send(request);
                        }
                    }
                }

                if (loading) {
                    waitCycles++;

                    if (waitCycles > 750) {
                        try {
                            socket.close();
                        } catch (Exception ignored) {
                        }
                        socket = null;
                        in = null;
                        out = null;
                        partAvailable = 0;
                    }
                } else {
                    waitCycles = 0;
                    message = "";
                }

                if (game.ingame && (socket != null) && (out != null) && ((topPriority > 0) || (game.filestores[0] == null))) {
                    heartbeatCycle++;

                    if (heartbeatCycle > 500) {
                        heartbeatCycle = 0;
                        buffer[0] = 0;
                        buffer[1] = 0;
                        buffer[2] = 0;
                        buffer[3] = 10;

                        try {
                            out.write(buffer, 0, 4);
                        } catch (IOException _ex) {
                            waitCycles = 5000;
                        }
                    }
                }
            }
        } catch (Exception exception) {
            Signlink.reporterror("od_ex " + exception.getMessage());
        }
    }

    public void prefetch(int file, int store) {
        if (game.filestores[0] == null) {
            return;
        }
        if (storeFileVersions[store][file] == 0) {
            return;
        }
        if (storeFilePriorities[store][file] == 0) {
            return;
        }
        if (topPriority == 0) {
            return;
        }
        OnDemandRequest request = new OnDemandRequest();
        request.store = store;
        request.file = file;
        request.important = false;
        synchronized (prefetches) {
            prefetches.pushBack(request);
        }
    }

    public OnDemandRequest poll() throws IOException {
        OnDemandRequest request;

        synchronized (completed) {
            request = (OnDemandRequest) completed.pollFront();
        }

        if (request == null) {
            return null;
        }

        synchronized (lock) {
            requests.remove(request);
        }

        if (request.data == null) {
            return request;
        }

        try (GzipCompressorInputStream gzis = new GzipCompressorInputStream(new ByteArrayInputStream(request.data))) {
            request.data = gzis.readAllBytes();
        }

        return request;
    }

    public int getMapFile(int type, int x, int z) {
        int index = (x << 8) + z;
        for (int i = 0; i < mapIndex.length; i++) {
            if (mapIndex[i] == index) {
                if (type == 0) {
                    return mapLandFile[i];
                } else {
                    return mapLocFile[i];
                }
            }
        }
        return -1;
    }

    public void requestModel(int id) {
        request(0, id);
    }

    public void prefetch(byte priority, int archive, int file) {
        if (game.filestores[0] == null) {
            return;
        }
        if (storeFileVersions[archive][file] == 0) {
            return;
        }

        byte[] data = game.filestores[archive + 1].read(file);

        if (validate(storeFileVersions[archive][file], storeFileChecksums[archive][file], data)) {
            return;
        }

        storeFilePriorities[archive][file] = priority;

        if (priority > topPriority) {
            topPriority = priority;
        }

        totalPrefetchFiles++;
    }

    public boolean hasMapLocFile(int fileID) {
        for (int i = 0; i < mapIndex.length; i++) {
            if (mapLocFile[i] == fileID) {
                return true;
            }
        }
        return false;
    }

    public void handlePending() {
        importantCount = 0;
        requestCount = 0;

        for (OnDemandRequest request = (OnDemandRequest) pending.peekFront(); request != null; request = (OnDemandRequest) pending.prev()) {
            if (request.important) {
                importantCount++;
            } else {
                requestCount++;
            }
        }

        while (importantCount < 10) {
            OnDemandRequest request = (OnDemandRequest) missing.pollFront();

            if (request == null) {
                break;
            }

            if (storeFilePriorities[request.store][request.file] != 0) {
                loadedPretechFiles++;
            }

            storeFilePriorities[request.store][request.file] = 0;
            pending.pushBack(request);
            importantCount++;
            send(request);
            active = true;
        }
    }

    public void clearPrefetches() {
        synchronized (prefetches) {
            prefetches.clear();
        }
    }

    public void handleQueue() {
        OnDemandRequest request;
        synchronized (queue) {
            request = (OnDemandRequest) queue.pollFront();
        }

        while (request != null) {
            active = true;
            byte[] data = null;

            if (game.filestores[0] != null) {
                data = game.filestores[request.store + 1].read(request.file);
            }

            synchronized (queue) {
                if (data == null) {
                    missing.pushBack(request);
                } else {
                    request.data = data;
                    synchronized (completed) {
                        completed.pushBack(request);
                    }
                }
                request = (OnDemandRequest) queue.pollFront();
            }
        }
    }

    public void handleExtras() {
        while ((importantCount == 0) && (requestCount < 10)) {
            if (topPriority == 0) {
                break;
            }

            OnDemandRequest extra;

            synchronized (prefetches) {
                extra = (OnDemandRequest) prefetches.pollFront();
            }

            while (extra != null) {
                if (storeFilePriorities[extra.store][extra.file] != 0) {
                    storeFilePriorities[extra.store][extra.file] = 0;
                    pending.pushBack(extra);
                    send(extra);
                    active = true;

                    if (loadedPretechFiles < totalPrefetchFiles) {
                        loadedPretechFiles++;
                    }

                    message = "Loading extra files - " + loadedPretechFiles * 100 / totalPrefetchFiles + "%";
                    requestCount++;

                    if (requestCount == 10) {
                        return;
                    }
                }
                synchronized (prefetches) {
                    extra = (OnDemandRequest) prefetches.pollFront();
                }
            }

            for (int store = 0; store < 4; store++) {
                byte[] priorities = storeFilePriorities[store];
                for (int file = 0; file < priorities.length; file++) {
                    if (priorities[file] != topPriority) {
                        continue;
                    }

                    priorities[file] = 0;

                    OnDemandRequest req = new OnDemandRequest();
                    req.store = store;
                    req.file = file;
                    req.important = false;
                    pending.pushBack(req);
                    send(req);
                    active = true;

                    if (loadedPretechFiles < totalPrefetchFiles) {
                        loadedPretechFiles++;
                    }

                    message = "Loading extra files - " + loadedPretechFiles * 100 / totalPrefetchFiles + "%";
                    requestCount++;

                    if (requestCount == 10) {
                        return;
                    }
                }
            }
            topPriority--;
        }
    }

    public boolean hasMidi(int i) {
        return midiIndex[i] == 1;
    }

}
