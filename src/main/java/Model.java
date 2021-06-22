// Decompiled by Jad v1.5.8f. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

public class Model extends Entity {

	public static final Model EMPTY = new Model();

	public static final int[] clippedX = new int[10];
	public static final int[] clippedY = new int[10];
	public static final int[] clippedColor = new int[10];
	public static final int[] pickedBitsets = new int[1000];

	public static int counter;

	public static int[] tmpVertexX = new int[2000];
	public static int[] tmpVertexY = new int[2000];
	public static int[] tmpVertexZ = new int[2000];
	public static int[] tmpFaceAlpha = new int[2000];

	public static Header[] headers;
	public static OnDemand ondemand;

	public static boolean[] faceClippedX = new boolean[4096];
	public static boolean[] faceNearClipped = new boolean[4096];

	public static int[] vertexScreenX = new int[4096];
	public static int[] vertexScreenY = new int[4096];
	public static int[] vertexScreenZ = new int[4096];
	public static int[] vertexViewSpaceX = new int[4096];
	public static int[] vertexViewSpaceY = new int[4096];
	public static int[] vertexViewSpaceZ = new int[4096];

	public static int[] tmpDepthFaceCount = new int[1500];
	public static int[][] tmpDepthFaces = new int[1500][512];
	public static int[] tmpPriorityFaceCount = new int[12];
	public static int[][] tmpPriorityFaces = new int[12][2000];
	public static int[] tmpPriority10FaceDepth = new int[2000];
	public static int[] tmpPriority11FaceDepth = new int[2000];
	public static int[] tmpPriorityDepthSum = new int[12];

	public static int baseX;
	public static int baseY;
	public static int baseZ;
	public static boolean checkHover;
	public static int mouseX;
	public static int mouseY;
	public static int pickedCount;
	public static int[] sin = Draw3D.sin;
	public static int[] cos = Draw3D.cos;
	public static int[] palette = Draw3D.palette;
	public static int[] reciprical16 = Draw3D.reciprical16;

	public static class Header {

		public byte[] data;
		public int vertexCount;
		public int faceCount;
		public int texturedFaceCount;
		public int obPoint1Position;
		public int obPoint2Position;
		public int obPoint3Position;
		public int obPoint4Position;
		public int obPoint5Position;
		public int obVertex1Position;
		public int obVertex2Position;
		public int obFace1Position;
		public int obFace2Position;
		public int obFace3Position;
		public int obFace4Position;
		public int obFace5Position;
		public int obAxisPosition;

	}

	public static void unload() {
		headers = null;
		faceClippedX = null;
		faceNearClipped = null;
		vertexScreenX = null;
		vertexScreenY = null;
		vertexScreenZ = null;
		vertexViewSpaceX = null;
		vertexViewSpaceY = null;
		vertexViewSpaceZ = null;
		tmpDepthFaceCount = null;
		tmpDepthFaces = null;
		tmpPriorityFaceCount = null;
		tmpPriorityFaces = null;
		tmpPriority10FaceDepth = null;
		tmpPriority11FaceDepth = null;
		tmpPriorityDepthSum = null;
		sin = null;
		cos = null;
		palette = null;
		reciprical16 = null;
	}

	public static void init(int count, OnDemand ondemand) {
		headers = new Header[count];
		Model.ondemand = ondemand;
	}

	public static void unpack(byte[] src, int id) {
		if (src == null) {
			Header header = headers[id] = new Header();
			header.vertexCount = 0;
			header.faceCount = 0;
			header.texturedFaceCount = 0;
			return;
		}
		Buffer buffer = new Buffer(src);
		buffer.position = src.length - 18;
		Header header = headers[id] = new Header();
		header.data = src;
		header.vertexCount = buffer.get2U();
		header.faceCount = buffer.get2U();
		header.texturedFaceCount = buffer.get1U();
		int k = buffer.get1U();
		int l = buffer.get1U();
		int i1 = buffer.get1U();
		int j1 = buffer.get1U();
		int k1 = buffer.get1U();
		int l1 = buffer.get2U();
		int i2 = buffer.get2U();
		//noinspection unused
		int j2 = buffer.get2U();
		int k2 = buffer.get2U();
		int offset = 0;
		header.obPoint1Position = offset;
		offset += header.vertexCount;
		header.obVertex2Position = offset;
		offset += header.faceCount;
		header.obFace3Position = offset;
		if (l == 255) {
			offset += header.faceCount;
		} else {
			header.obFace3Position = -l - 1;
		}
		header.obFace5Position = offset;
		if (j1 == 1) {
			offset += header.faceCount;
		} else {
			header.obFace5Position = -1;
		}
		header.obFace2Position = offset;
		if (k == 1) {
			offset += header.faceCount;
		} else {
			header.obFace2Position = -1;
		}
		header.obPoint5Position = offset;
		if (k1 == 1) {
			offset += header.vertexCount;
		} else {
			header.obPoint5Position = -1;
		}
		header.obFace4Position = offset;
		if (i1 == 1) {
			offset += header.faceCount;
		} else {
			header.obFace4Position = -1;
		}
		header.obVertex1Position = offset;
		offset += k2;
		header.obFace1Position = offset;
		offset += header.faceCount * 2;
		header.obAxisPosition = offset;
		offset += header.texturedFaceCount * 6;
		header.obPoint2Position = offset;
		offset += l1;
		header.obPoint3Position = offset;
		offset += i2;
		header.obPoint4Position = offset;
	}

	public static void unload(int id) {
		headers[id] = null;
	}

	/**
	 * Tries to get the model. Sends a request to the ondemand service to load the model if it isn't loaded.
	 *
	 * @param id the model id.
	 * @return the model, or <code>null</code> if not loaded.
	 */
	public static Model tryGet(int id) {
		if (headers == null) {
			return null;
		}

		Header header = headers[id];

		if (header != null) {
			return new Model(id);
		} else {
			ondemand.requestModel(id);
			return null;
		}
	}

	/**
	 * Validates the provided model id. If the model is not loaded then a request is sent to the ondemand service.
	 *
	 * @param id the model id.
	 * @return <code>true</code> if the model is loaded.
	 */
	public static boolean validate(int id) {
		if (headers == null) {
			return false;
		}

		Header header = headers[id];

		if (header != null) {
			return true;
		} else {
			ondemand.requestModel(id);
			return false;
		}
	}

	/**
	 * Utility function. Multiplies the input HSL lightness component by the provided <code>scalar</code>.
	 *
	 * @param hsl      the color value.
	 * @param scalar   the scalar. [0...127]
	 * @param faceInfo Provided face info to determine the type of color to return. Textured triangles (type 2) only have
	 *                 a lightness component.
	 * @return the color.
	 * @see #palette
	 */
	public static int mulColorLightness(int hsl, int scalar, int faceInfo) {
		if ((faceInfo & 2) == 2) {
			if (scalar < 0) {
				scalar = 0;
			} else if (scalar > 127) {
				scalar = 127;
			}
			scalar = 127 - scalar;
			return scalar;
		}
		scalar = (scalar * (hsl & 0x7f)) >> 7;
		if (scalar < 2) {
			scalar = 2;
		} else if (scalar > 126) {
			scalar = 126;
		}
		return (hsl & 0xff80) + scalar;
	}

	public int vertexCount;
	public int[] vertexX;
	public int[] vertexY;
	public int[] vertexZ;
	public int faceCount;
	public int[] faceVertexA;
	public int[] faceVertexB;
	public int[] faceVertexC;
	public int[] faceColorA;
	public int[] faceColorB;
	public int[] faceColorC;
	public int[] faceInfo;
	public int[] facePriority;
	public int[] faceAlpha;
	public int[] faceColor;
	public int priority;
	public int texturedFaceCount;
	public int[] texturedVertexA;
	public int[] texturedVertexB;
	public int[] texturedVertexC;
	public int minX;
	public int maxX;
	public int maxY;
	public int minZ;
	public int maxZ;
	/**
	 * The radius of this model on the XZ plane.
	 *
	 * @see #calculateBoundsAABB()
	 */
	public int radius;
	public int minDepth;
	public int maxDepth;
	/**
	 * minDepth = (int) Math.sqrt((radius * radius) + (super.minY * super.minY));
	 * maxDepth = minDepth + (int) Math.sqrt((radius * radius) + (maxY * maxY));
	 */
	public int objRaise;
	/**
	 * The label the vertex belongs to.
	 */
	public int[] vertexLabel;
	/**
	 * The label the face belongs to.
	 */
	public int[] faceLabel;
	/**
	 * A lookup table for label->vertex.
	 */
	public int[][] labelVertices;
	/**
	 * A lookup table for label->face.
	 */
	public int[][] labelFaces;
	/**
	 * When set to <code>true</code>, this model will be picked based on its projected screen bounds.
	 *
	 * @see #draw(int, int, int, int, int, int, int, int, int)
	 */
	public boolean pickable = false;
	/**
	 * A storage for the original vertex normals to give {@link Scene#method308(Model, Model, int, int, int, boolean)}
	 * a reference.
	 */
	public VertexNormal[] vertexNormalOriginal;

	/**
	 * Constructs an empty model.
	 */
	public Model() {
	}

	/**
	 * Constructs a new model and loads it from its header.
	 *
	 * <b>Note:</b> This constructor only works if the model has already been loaded. {@link #unpack(byte[], int)} must
	 * have already been called for the given <code>id</code>.
	 *
	 * @param id the model id.
	 */
	public Model(int id) {
		counter++;

		Header header = headers[id];
		vertexCount = header.vertexCount;
		faceCount = header.faceCount;
		texturedFaceCount = header.texturedFaceCount;
		vertexX = new int[vertexCount];
		vertexY = new int[vertexCount];
		vertexZ = new int[vertexCount];
		faceVertexA = new int[faceCount];
		faceVertexB = new int[faceCount];
		faceVertexC = new int[faceCount];
		texturedVertexA = new int[texturedFaceCount];
		texturedVertexB = new int[texturedFaceCount];
		texturedVertexC = new int[texturedFaceCount];

		if (header.obPoint5Position >= 0) {
			vertexLabel = new int[vertexCount];
		}

		if (header.obFace2Position >= 0) {
			faceInfo = new int[faceCount];
		}

		if (header.obFace3Position >= 0) {
			facePriority = new int[faceCount];
		} else {
			priority = -header.obFace3Position - 1;
		}

		if (header.obFace4Position >= 0) {
			faceAlpha = new int[faceCount];
		}

		if (header.obFace5Position >= 0) {
			faceLabel = new int[faceCount];
		}

		faceColor = new int[faceCount];

		Buffer buf0 = new Buffer(header.data);
		buf0.position = header.obPoint1Position;

		Buffer buf1 = new Buffer(header.data);
		buf1.position = header.obPoint2Position;

		Buffer buf2 = new Buffer(header.data);
		buf2.position = header.obPoint3Position;

		Buffer buf3 = new Buffer(header.data);
		buf3.position = header.obPoint4Position;

		Buffer buf4 = new Buffer(header.data);
		buf4.position = header.obPoint5Position;

		int x = 0;
		int y = 0;
		int z = 0;

		for (int v = 0; v < vertexCount; v++) {
			int flags = buf0.get1U();

			int dx = 0;
			int dy = 0;
			int dz = 0;

			if ((flags & 1) != 0) {
				dx = buf1.getSmart();
			}

			if ((flags & 2) != 0) {
				dy = buf2.getSmart();
			}

			if ((flags & 4) != 0) {
				dz = buf3.getSmart();
			}

			vertexX[v] = x + dx;
			vertexY[v] = y + dy;
			vertexZ[v] = z + dz;

			x = vertexX[v];
			y = vertexY[v];
			z = vertexZ[v];

			if (vertexLabel != null) {
				vertexLabel[v] = buf4.get1U();
			}
		}

		buf0.position = header.obFace1Position;
		buf1.position = header.obFace2Position;
		buf2.position = header.obFace3Position;
		buf3.position = header.obFace4Position;
		buf4.position = header.obFace5Position;

		for (int face = 0; face < faceCount; face++) {
			faceColor[face] = buf0.get2U();

			if (faceInfo != null) {
				faceInfo[face] = buf1.get1U();
			}

			if (facePriority != null) {
				facePriority[face] = buf2.get1U();
			}

			if (faceAlpha != null) {
				faceAlpha[face] = buf3.get1U();
			}

			if (faceLabel != null) {
				faceLabel[face] = buf4.get1U();
			}
		}

		buf0.position = header.obVertex1Position;
		buf1.position = header.obVertex2Position;

		int a = 0;
		int b = 0;
		int c = 0;
		int last = 0;

		for (int face = 0; face < faceCount; face++) {
			int orientation = buf1.get1U();

			// fancy shmansy compression type stuff.
			// vertex indices stored as deltas, with some faces
			// sharing indices.

			// new a, b, c
			if (orientation == 1) {
				a = buf0.getSmart() + last;
				last = a;
				b = buf0.getSmart() + last;
				last = b;
				c = buf0.getSmart() + last;
				last = c;
				faceVertexA[face] = a;
				faceVertexB[face] = b;
				faceVertexC[face] = c;
			}

			// reuse a, c, new b
			if (orientation == 2) {
				b = c;
				c = buf0.getSmart() + last;
				last = c;
				faceVertexA[face] = a;
				faceVertexB[face] = b;
				faceVertexC[face] = c;
			}

			// reuse c, b, new a
			if (orientation == 3) {
				a = c;
				c = buf0.getSmart() + last;
				last = c;
				faceVertexA[face] = a;
				faceVertexB[face] = b;
				faceVertexC[face] = c;
			}

			// reuse b, a, new c
			if (orientation == 4) {
				int a_ = a;
				a = b;
				b = a_;
				c = buf0.getSmart() + last;
				last = c;
				faceVertexA[face] = a;
				faceVertexB[face] = b;
				faceVertexC[face] = c;
			}
		}

		buf0.position = header.obAxisPosition;

		for (int face = 0; face < texturedFaceCount; face++) {
			texturedVertexA[face] = buf0.get2U();
			texturedVertexB[face] = buf0.get2U();
			texturedVertexC[face] = buf0.get2U();
		}
	}

	/**
	 * Constructs a new model by merging the provided models. This constructor is used to combine models <i>before</i>
	 * {@link SeqFrame}'s have been applied. Using this constructor implies all models merged are compatible with any
	 * animations played on them.
	 *
	 * @param count  the model count.
	 * @param models the models to merge.
	 */
	public Model(int count, Model[] models) {
		counter++;

		boolean copyInfo = false;
		boolean copyPriority = false;
		boolean copyAlpha = false;
		boolean copyLabels = false;

		vertexCount = 0;
		faceCount = 0;
		texturedFaceCount = 0;
		priority = -1;

		for (int i = 0; i < count; i++) {
			Model model = models[i];

			if (model == null) {
				continue;
			}

			vertexCount += model.vertexCount;
			faceCount += model.faceCount;
			texturedFaceCount += model.texturedFaceCount;

			copyInfo |= model.faceInfo != null;

			if (model.facePriority != null) {
				copyPriority = true;
			} else {
				if (priority == -1) {
					priority = model.priority;
				}
				if (priority != model.priority) {
					copyPriority = true;
				}
			}

			copyAlpha |= model.faceAlpha != null;
			copyLabels |= model.faceLabel != null;
		}

		vertexX = new int[vertexCount];
		vertexY = new int[vertexCount];
		vertexZ = new int[vertexCount];
		vertexLabel = new int[vertexCount];
		faceVertexA = new int[faceCount];
		faceVertexB = new int[faceCount];
		faceVertexC = new int[faceCount];
		texturedVertexA = new int[texturedFaceCount];
		texturedVertexB = new int[texturedFaceCount];
		texturedVertexC = new int[texturedFaceCount];

		if (copyInfo) {
			faceInfo = new int[faceCount];
		}

		if (copyPriority) {
			facePriority = new int[faceCount];
		}

		if (copyAlpha) {
			faceAlpha = new int[faceCount];
		}

		if (copyLabels) {
			faceLabel = new int[faceCount];
		}

		faceColor = new int[faceCount];
		vertexCount = 0;
		faceCount = 0;
		texturedFaceCount = 0;

		int tfaceCount = 0;

		for (int i = 0; i < count; i++) {
			Model model = models[i];

			if (model == null) {
				continue;
			}

			for (int face = 0; face < model.faceCount; face++) {
				if (copyInfo) {
					if (model.faceInfo == null) {
						faceInfo[faceCount] = 0;
					} else {
						int info = model.faceInfo[face];

						if ((info & 2) == 2) {
							info += tfaceCount << 2;
						}

						faceInfo[faceCount] = info;
					}
				}

				if (copyPriority) {
					if (model.facePriority == null) {
						facePriority[faceCount] = model.priority;
					} else {
						facePriority[faceCount] = model.facePriority[face];
					}
				}

				if (copyAlpha) {
					if (model.faceAlpha == null) {
						faceAlpha[faceCount] = 0;
					} else {
						faceAlpha[faceCount] = model.faceAlpha[face];
					}
				}

				if (copyLabels && (model.faceLabel != null)) {
					faceLabel[faceCount] = model.faceLabel[face];
				}

				faceColor[faceCount] = model.faceColor[face];
				faceVertexA[faceCount] = addVertex(model, model.faceVertexA[face]);
				faceVertexB[faceCount] = addVertex(model, model.faceVertexB[face]);
				faceVertexC[faceCount] = addVertex(model, model.faceVertexC[face]);
				faceCount++;
			}

			for (int tface = 0; tface < model.texturedFaceCount; tface++) {
				texturedVertexA[texturedFaceCount] = addVertex(model, model.texturedVertexA[tface]);
				texturedVertexB[texturedFaceCount] = addVertex(model, model.texturedVertexB[tface]);
				texturedVertexC[texturedFaceCount] = addVertex(model, model.texturedVertexC[tface]);
				texturedFaceCount++;
			}

			tfaceCount += model.texturedFaceCount;
		}
	}

	/**
	 * Constructs a new model by combining models. This constructor is commonly used to combine models which have already
	 * had a {@link SeqFrame} applied, and does not copy label information.
	 *
	 * @param count  the model count.
	 * @param dummy  dummy.
	 * @param models the models.
	 */
	public Model(int count, int dummy, Model[] models) {
		counter++;

		boolean copyInfo = false;
		boolean copyPriority = false;
		boolean copyAlpha = false;
		boolean copyColor = false;

		vertexCount = 0;
		faceCount = 0;
		texturedFaceCount = 0;
		priority = -1;

		for (int i = 0; i < count; i++) {
			Model model = models[i];

			if (model == null) {
				continue;
			}

			vertexCount += model.vertexCount;
			faceCount += model.faceCount;
			texturedFaceCount += model.texturedFaceCount;
			copyInfo |= model.faceInfo != null;

			if (model.facePriority != null) {
				copyPriority = true;
			} else {
				if (priority == -1) {
					priority = model.priority;
				}
				if (priority != model.priority) {
					copyPriority = true;
				}
			}

			copyAlpha |= model.faceAlpha != null;
			copyColor |= model.faceColor != null;
		}

		vertexX = new int[vertexCount];
		vertexY = new int[vertexCount];
		vertexZ = new int[vertexCount];
		faceVertexA = new int[faceCount];
		faceVertexB = new int[faceCount];
		faceVertexC = new int[faceCount];
		faceColorA = new int[faceCount];
		faceColorB = new int[faceCount];
		faceColorC = new int[faceCount];
		texturedVertexA = new int[texturedFaceCount];
		texturedVertexB = new int[texturedFaceCount];
		texturedVertexC = new int[texturedFaceCount];

		if (copyInfo) {
			faceInfo = new int[faceCount];
		}

		if (copyPriority) {
			facePriority = new int[faceCount];
		}

		if (copyAlpha) {
			faceAlpha = new int[faceCount];
		}

		if (copyColor) {
			faceColor = new int[faceCount];
		}

		vertexCount = 0;
		faceCount = 0;
		texturedFaceCount = 0;

		int tfaceCount = 0;
		for (int i = 0; i < count; i++) {
			Model model = models[i];

			if (model == null) {
				continue;
			}

			int vertexCount = this.vertexCount;

			for (int v = 0; v < model.vertexCount; v++) {
				vertexX[this.vertexCount] = model.vertexX[v];
				vertexY[this.vertexCount] = model.vertexY[v];
				vertexZ[this.vertexCount] = model.vertexZ[v];
				this.vertexCount++;
			}

			for (int f = 0; f < model.faceCount; f++) {
				faceVertexA[faceCount] = model.faceVertexA[f] + vertexCount;
				faceVertexB[faceCount] = model.faceVertexB[f] + vertexCount;
				faceVertexC[faceCount] = model.faceVertexC[f] + vertexCount;

				faceColorA[faceCount] = model.faceColorA[f];
				faceColorB[faceCount] = model.faceColorB[f];
				faceColorC[faceCount] = model.faceColorC[f];

				if (copyInfo) {
					if (model.faceInfo == null) {
						faceInfo[faceCount] = 0;
					} else {
						int info = model.faceInfo[f];

						if ((info & 2) == 2) {
							info += tfaceCount << 2;
						}

						faceInfo[faceCount] = info;
					}
				}

				if (copyPriority) {
					if (model.facePriority == null) {
						facePriority[faceCount] = model.priority;
					} else {
						facePriority[faceCount] = model.facePriority[f];
					}
				}

				if (copyAlpha) {
					if (model.faceAlpha == null) {
						faceAlpha[faceCount] = 0;
					} else {
						faceAlpha[faceCount] = model.faceAlpha[f];
					}
				}

				if (copyColor && (model.faceColor != null)) {
					faceColor[faceCount] = model.faceColor[f];
				}
				faceCount++;
			}

			for (int f = 0; f < model.texturedFaceCount; f++) {
				texturedVertexA[texturedFaceCount] = model.texturedVertexA[f] + vertexCount;
				texturedVertexB[texturedFaceCount] = model.texturedVertexB[f] + vertexCount;
				texturedVertexC[texturedFaceCount] = model.texturedVertexC[f] + vertexCount;
				texturedFaceCount++;
			}

			tfaceCount += model.texturedFaceCount;
		}
		calculateBoundsCylinder();
	}

	/**
	 * Constructs a new model that can either share or clone the provided models attributes. This constructor is used to
	 * save memory by avoiding allocations, and is meant to be used with models prior to having a {@link SeqFrame} applied.
	 *
	 * @param shareColors   <code>true</code> to reference the provided model's colors.
	 * @param shareAlpha    <code>true</code> to reference the provided model's face alpha.
	 * @param shareVertices <code>true</code> to reference the provided model's vertices.
	 * @param model         the model to share or clone.
	 */
	public Model(boolean shareColors, boolean shareAlpha, boolean shareVertices, Model model) {
		counter++;
		vertexCount = model.vertexCount;
		faceCount = model.faceCount;
		texturedFaceCount = model.texturedFaceCount;

		if (shareVertices) {
			vertexX = model.vertexX;
			vertexY = model.vertexY;
			vertexZ = model.vertexZ;
		} else {
			vertexX = new int[vertexCount];
			vertexY = new int[vertexCount];
			vertexZ = new int[vertexCount];

			for (int j = 0; j < vertexCount; j++) {
				vertexX[j] = model.vertexX[j];
				vertexY[j] = model.vertexY[j];
				vertexZ[j] = model.vertexZ[j];
			}
		}

		if (shareColors) {
			faceColor = model.faceColor;
		} else {
			faceColor = new int[faceCount];

			for (int k = 0; k < faceCount; k++) {
				faceColor[k] = model.faceColor[k];
			}
		}

		if (shareAlpha) {
			faceAlpha = model.faceAlpha;
		} else {
			faceAlpha = new int[faceCount];
			if (model.faceAlpha == null) {
				for (int l = 0; l < faceCount; l++) {
					faceAlpha[l] = 0;
				}
			} else {
				for (int i1 = 0; i1 < faceCount; i1++) {
					faceAlpha[i1] = model.faceAlpha[i1];
				}
			}
		}

		vertexLabel = model.vertexLabel;
		faceLabel = model.faceLabel;
		faceInfo = model.faceInfo;
		faceVertexA = model.faceVertexA;
		faceVertexB = model.faceVertexB;
		faceVertexC = model.faceVertexC;
		facePriority = model.facePriority;
		priority = model.priority;
		texturedVertexA = model.texturedVertexA;
		texturedVertexB = model.texturedVertexB;
		texturedVertexC = model.texturedVertexC;
	}

	/**
	 * Constructs a new model with the options to copy either vertex Y positions and faces.
	 * <p>
	 * This constructor is specifically used by Locs which adjust to terrain.
	 *
	 * @param copyVertexY <code>true</code> to copy <code>model.vertexY</code>.
	 * @param copyFaces   <code>true</code> to copy all face data from <code>model</code>.
	 * @param model       the model to copy.
	 * @see LocType#method578(int, int, int, int, int, int, int)
	 */
	public Model(boolean copyVertexY, boolean copyFaces, Model model) {
		counter++;
		vertexCount = model.vertexCount;
		faceCount = model.faceCount;
		texturedFaceCount = model.texturedFaceCount;

		if (copyVertexY) {
			vertexY = new int[vertexCount];
			for (int v = 0; v < vertexCount; v++) {
				vertexY[v] = model.vertexY[v];
			}
		} else {
			vertexY = model.vertexY;
		}

		if (copyFaces) {
			faceColorA = new int[faceCount];
			faceColorB = new int[faceCount];
			faceColorC = new int[faceCount];

			for (int k = 0; k < faceCount; k++) {
				faceColorA[k] = model.faceColorA[k];
				faceColorB[k] = model.faceColorB[k];
				faceColorC[k] = model.faceColorC[k];
			}

			faceInfo = new int[faceCount];

			if (model.faceInfo == null) {
				for (int l = 0; l < faceCount; l++) {
					faceInfo[l] = 0;
				}
			} else {
				for (int face = 0; face < faceCount; face++) {
					faceInfo[face] = model.faceInfo[face];
				}
			}

			super.vertexNormal = new VertexNormal[vertexCount];
			for (int v = 0; v < vertexCount; v++) {
				VertexNormal duplicate = super.vertexNormal[v] = new VertexNormal();
				VertexNormal original = model.vertexNormal[v];
				duplicate.x = original.x;
				duplicate.y = original.y;
				duplicate.z = original.z;
				duplicate.w = original.w;
			}
			vertexNormalOriginal = model.vertexNormalOriginal;
		} else {
			faceColorA = model.faceColorA;
			faceColorB = model.faceColorB;
			faceColorC = model.faceColorC;
			faceInfo = model.faceInfo;
		}

		vertexX = model.vertexX;
		vertexZ = model.vertexZ;
		faceColor = model.faceColor;
		faceAlpha = model.faceAlpha;
		facePriority = model.facePriority;
		priority = model.priority;
		faceVertexA = model.faceVertexA;
		faceVertexB = model.faceVertexB;
		faceVertexC = model.faceVertexC;
		texturedVertexA = model.texturedVertexA;
		texturedVertexB = model.texturedVertexB;
		texturedVertexC = model.texturedVertexC;
		super.minY = model.minY;
		maxY = model.maxY;
		radius = model.radius;
		minDepth = model.minDepth;
		maxDepth = model.maxDepth;
		minX = model.minX;
		maxZ = model.maxZ;
		minZ = model.minZ;
		maxX = model.maxX;
	}

	/**
	 * Sets <code>this</code> model to the provided model using a vertex pool.
	 * <p>
	 * Face alpha may be optionally copied in case an applied {@link SeqFrame} modifies the alpha of the model.
	 *
	 * @param model      the model.
	 * @param shareAlpha whether to copy or share a reference to face alphas.
	 */
	public void set(Model model, boolean shareAlpha) {
		vertexCount = model.vertexCount;
		faceCount = model.faceCount;
		texturedFaceCount = model.texturedFaceCount;

		if (tmpVertexX.length < vertexCount) {
			tmpVertexX = new int[vertexCount + 100];
			tmpVertexY = new int[vertexCount + 100];
			tmpVertexZ = new int[vertexCount + 100];
		}

		vertexX = tmpVertexX;
		vertexY = tmpVertexY;
		vertexZ = tmpVertexZ;

		for (int k = 0; k < vertexCount; k++) {
			vertexX[k] = model.vertexX[k];
			vertexY[k] = model.vertexY[k];
			vertexZ[k] = model.vertexZ[k];
		}

		if (shareAlpha) {
			faceAlpha = model.faceAlpha;
		} else {
			if (tmpFaceAlpha.length < faceCount) {
				tmpFaceAlpha = new int[faceCount + 100];
			}
			faceAlpha = tmpFaceAlpha;
			if (model.faceAlpha == null) {
				for (int face = 0; face < faceCount; face++) {
					faceAlpha[face] = 0;
				}
			} else {
				for (int face = 0; face < faceCount; face++) {
					faceAlpha[face] = model.faceAlpha[face];
				}
			}
		}

		faceInfo = model.faceInfo;
		faceColor = model.faceColor;
		facePriority = model.facePriority;
		priority = model.priority;
		labelFaces = model.labelFaces;
		labelVertices = model.labelVertices;
		faceVertexA = model.faceVertexA;
		faceVertexB = model.faceVertexB;
		faceVertexC = model.faceVertexC;
		faceColorA = model.faceColorA;
		faceColorB = model.faceColorB;
		faceColorC = model.faceColorC;
		texturedVertexA = model.texturedVertexA;
		texturedVertexB = model.texturedVertexB;
		texturedVertexC = model.texturedVertexC;
	}

	/**
	 * Adds <code>vertexId</code> from <code>src</code> to <code>this</code>. Reuses vertex if one already exists at the
	 * same location.
	 *
	 * @param src      the source model.
	 * @param vertexId the vertex id to add from the <code>src</code>.
	 * @return the vertex id of the added vertex.
	 */
	public int addVertex(Model src, int vertexId) {
		int x = src.vertexX[vertexId];
		int y = src.vertexY[vertexId];
		int z = src.vertexZ[vertexId];

		int identical = -1;
		for (int v = 0; v < vertexCount; v++) {
			if ((x == vertexX[v]) && (y == vertexY[v]) && (z == vertexZ[v])) {
				identical = v;
				break;
			}
		}

		// append new one if no matches were found
		if (identical == -1) {
			vertexX[vertexCount] = x;
			vertexY[vertexCount] = y;
			vertexZ[vertexCount] = z;
			if (src.vertexLabel != null) {
				vertexLabel[vertexCount] = src.vertexLabel[vertexId];
			}
			identical = vertexCount++;
		}

		return identical;
	}

	/**
	 * Calculates {@link #minY}, {@link #maxY}, {@link #radius}, {@link #minDepth} and {@link #maxDepth}.
	 */
	public void calculateBoundsCylinder() {
		super.minY = 0;
		radius = 0;
		maxY = 0;
		for (int i = 0; i < vertexCount; i++) {
			int x = vertexX[i];
			int y = vertexY[i];
			int z = vertexZ[i];
			if (-y > super.minY) {
				super.minY = -y;
			}
			if (y > maxY) {
				maxY = y;
			}
			int radiusSqr = (x * x) + (z * z);
			if (radiusSqr > radius) {
				radius = radiusSqr;
			}
		}
		radius = (int) (Math.sqrt(radius) + 0.99);
		minDepth = (int) (Math.sqrt((radius * radius) + (super.minY * super.minY)) + 0.99);
		maxDepth = minDepth + (int) (Math.sqrt((radius * radius) + (maxY * maxY)) + 0.99);
	}

	/**
	 * Calculates {@link #minY}, {@link #maxY}, {@link #minDepth} and {@link #maxDepth}.
	 */
	public void calculateBoundsY() {
		super.minY = 0;
		maxY = 0;
		for (int i = 0; i < vertexCount; i++) {
			int y = vertexY[i];
			if (-y > super.minY) {
				super.minY = -y;
			}
			if (y > maxY) {
				maxY = y;
			}
		}
		minDepth = (int) (Math.sqrt((radius * radius) + (super.minY * super.minY)) + 0.99);
		maxDepth = minDepth + (int) (Math.sqrt((radius * radius) + (maxY * maxY)) + 0.99);
	}

	/**
	 * Calculates this models axis-aligned bounding box (AABB) and stores it.
	 */
	public void calculateBoundsAABB() {
		super.minY = 0;
		radius = 0;
		maxY = 0;
		minX = 999999;
		maxX = -999999;
		maxZ = -99999;
		minZ = 99999;
		for (int j = 0; j < vertexCount; j++) {
			int x = vertexX[j];
			int y = vertexY[j];
			int z = vertexZ[j];
			if (x < minX) {
				minX = x;
			}
			if (x > maxX) {
				maxX = x;
			}
			if (z < minZ) {
				minZ = z;
			}
			if (z > maxZ) {
				maxZ = z;
			}
			if (-y > super.minY) {
				super.minY = -y;
			}
			if (y > maxY) {
				maxY = y;
			}
			int radiusSqr = (x * x) + (z * z);
			if (radiusSqr > radius) {
				radius = radiusSqr;
			}
		}
		radius = (int) Math.sqrt(radius);
		minDepth = (int) Math.sqrt((radius * radius) + (super.minY * super.minY));
		maxDepth = minDepth + (int) Math.sqrt((radius * radius) + (maxY * maxY));
	}

	/**
	 * Builds {@link #labelVertices} and {@link #labelFaces} using {@link #vertexLabel} and {@link #faceLabel}.
	 * <p>
	 * This method is <i>destructive</i>, meaning it sets {@link #vertexLabel} and {@link #faceLabel} to <code>null</code>
	 * after being called.
	 * <p>
	 * This method is required for applying animations to a model and should only be called once on a single instance
	 * of a {@link Model}.
	 *
	 * @see #applySequenceFrame(int)
	 * @see #applySequenceFrames(int, int, int[])
	 * @see #applyTransform(int, int[], int, int, int)
	 */
	public void createLabelReferences() {
		if (vertexLabel != null) {
			int[] labelVertexCount = new int[256];

			int count = 0;
			for (int v = 0; v < vertexCount; v++) {
				int label = vertexLabel[v];
				labelVertexCount[label]++;
				if (label > count) {
					count = label;
				}
			}

			labelVertices = new int[count + 1][];

			for (int label = 0; label <= count; label++) {
				labelVertices[label] = new int[labelVertexCount[label]];
				labelVertexCount[label] = 0;
			}

			for (int v = 0; v < vertexCount; v++) {
				int label = vertexLabel[v];
				labelVertices[label][labelVertexCount[label]++] = v;
			}

			vertexLabel = null;
		}

		if (faceLabel != null) {
			int[] labelFaceCount = new int[256];

			int count = 0;
			for (int f = 0; f < faceCount; f++) {
				int label = faceLabel[f];
				labelFaceCount[label]++;
				if (label > count) {
					count = label;
				}
			}

			labelFaces = new int[count + 1][];
			for (int label = 0; label <= count; label++) {
				labelFaces[label] = new int[labelFaceCount[label]];
				labelFaceCount[label] = 0;
			}

			for (int face = 0; face < faceCount; face++) {
				int label = faceLabel[face];
				labelFaces[label][labelFaceCount[label]++] = face;
			}

			faceLabel = null;
		}
	}

	/**
	 * Applies a {@link SeqFrame} of the specified <code>id</code>.
	 *
	 * @param frameId the frame id.
	 */
	public void applySequenceFrame(int frameId) {
		if (labelVertices == null) {
			return;
		}
		if (frameId == -1) {
			return;
		}
		SeqFrame frame = SeqFrame.get(frameId);
		if (frame == null) {
			return;
		}
		SeqSkeleton skeleton = frame.skeleton;
		baseX = 0;
		baseY = 0;
		baseZ = 0;
		for (int i = 0; i < frame.length; i++) {
			int base = frame.bases[i];
			applyTransform(skeleton.baseTypes[base], skeleton.baseLabels[base], frame.x[i], frame.y[i], frame.z[i]);
		}
	}

	/**
	 * Applies the specified {@link SeqFrame} ids.
	 *
	 * @param frameId1 the primary frame id.
	 * @param frameId2 the secondary frame id.
	 * @param mask     the mask contains base ids to prevent the primary frame from using the same bases as the secondary frame.
	 */
	public void applySequenceFrames(int frameId1, int frameId2, int[] mask) {
		if (frameId1 == -1) {
			return;
		}

		if ((mask == null) || (frameId2 == -1)) {
			applySequenceFrame(frameId1);
			return;
		}

		SeqFrame frame0 = SeqFrame.get(frameId1);

		if (frame0 == null) {
			return;
		}

		SeqFrame frame1 = SeqFrame.get(frameId2);

		if (frame1 == null) {
			applySequenceFrame(frameId1);
			return;
		}

		SeqSkeleton skeleton = frame0.skeleton;

		baseX = 0;
		baseY = 0;
		baseZ = 0;

		int counter = 0;
		int maskBase = mask[counter++];

		for (int i = 0; i < frame0.length; i++) {
			int base = frame0.bases[i];

			while (base > maskBase) {
				maskBase = mask[counter++];
			}

			if ((base != maskBase) || (skeleton.baseTypes[base] == 0)) {
				applyTransform(skeleton.baseTypes[base], skeleton.baseLabels[base], frame0.x[i], frame0.y[i], frame0.z[i]);
			}
		}

		baseX = 0;
		baseY = 0;
		baseZ = 0;

		counter = 0;
		maskBase = mask[counter++];

		for (int frame = 0; frame < frame1.length; frame++) {
			int group = frame1.bases[frame];

			while (group > maskBase) {
				maskBase = mask[counter++];
			}

			if ((group == maskBase) || (skeleton.baseTypes[group] == 0)) {
				applyTransform(skeleton.baseTypes[group], skeleton.baseLabels[group], frame1.x[frame], frame1.y[frame], frame1.z[frame]);
			}
		}
	}

	/**
	 * Applies a transform of the given <code>type</code> to the specified list of <code>labels</code> using the parameters
	 * <code>x, y, z</code>.
	 *
	 * @param type   the transform type.
	 * @param labels the transform labels.
	 * @param x      the param x.
	 * @param y      the param y.
	 * @param z      the param z.
	 * @see #applySequenceFrames(int, int, int[])
	 * @see #applySequenceFrame(int)
	 * @see SeqSkeleton#OP_BASE
	 * @see SeqSkeleton#OP_TRANSLATE
	 * @see SeqSkeleton#OP_ROTATE
	 * @see SeqSkeleton#OP_SCALE
	 * @see SeqSkeleton#OP_ALPHA
	 */
	public void applyTransform(int type, int[] labels, int x, int y, int z) {
		switch (type) {
			case SeqSkeleton.OP_BASE:
				int count = 0;
				baseX = 0;
				baseY = 0;
				baseZ = 0;
				for (int label : labels) {
					if (label >= labelVertices.length) {
						continue;
					}
					int[] vertices = labelVertices[label];
					for (int v : vertices) {
						baseX += vertexX[v];
						baseY += vertexY[v];
						baseZ += vertexZ[v];
						count++;
					}
				}
				if (count > 0) {
					baseX = (baseX / count) + x;
					baseY = (baseY / count) + y;
					baseZ = (baseZ / count) + z;
				} else {
					baseX = x;
					baseY = y;
					baseZ = z;
				}
				break;
			case SeqSkeleton.OP_TRANSLATE:
				for (int group : labels) {
					if (group >= labelVertices.length) {
						continue;
					}
					int[] vertices = labelVertices[group];
					for (int v : vertices) {
						vertexX[v] += x;
						vertexY[v] += y;
						vertexZ[v] += z;
					}
				}
				break;
			case SeqSkeleton.OP_ROTATE:
				for (int label : labels) {
					if (label >= labelVertices.length) {
						continue;
					}
					int[] vertices = labelVertices[label];
					for (int v : vertices) {
						vertexX[v] -= baseX;
						vertexY[v] -= baseY;
						vertexZ[v] -= baseZ;
						int pitch = (x & 0xff) * 8;
						int yaw = (y & 0xff) * 8;
						int roll = (z & 0xff) * 8;
						if (roll != 0) {
							int sin = Model.sin[roll];
							int cos = Model.cos[roll];
							int x_ = ((vertexY[v] * sin) + (vertexX[v] * cos)) >> 16;
							vertexY[v] = ((vertexY[v] * cos) - (vertexX[v] * sin)) >> 16;
							vertexX[v] = x_;
						}
						if (pitch != 0) {
							int sin = Model.sin[pitch];
							int cos = Model.cos[pitch];
							int y_ = ((vertexY[v] * cos) - (vertexZ[v] * sin)) >> 16;
							vertexZ[v] = ((vertexY[v] * sin) + (vertexZ[v] * cos)) >> 16;
							vertexY[v] = y_;
						}
						if (yaw != 0) {
							int sin = Model.sin[yaw];
							int cos = Model.cos[yaw];
							int x_ = ((vertexZ[v] * sin) + (vertexX[v] * cos)) >> 16;
							vertexZ[v] = ((vertexZ[v] * cos) - (vertexX[v] * sin)) >> 16;
							vertexX[v] = x_;
						}
						vertexX[v] += baseX;
						vertexY[v] += baseY;
						vertexZ[v] += baseZ;
					}
				}
				break;
			case SeqSkeleton.OP_SCALE:
				for (int label : labels) {
					if (label >= labelVertices.length) {
						continue;
					}
					int[] vertices = labelVertices[label];
					for (int v : vertices) {
						vertexX[v] -= baseX;
						vertexY[v] -= baseY;
						vertexZ[v] -= baseZ;
						vertexX[v] = (vertexX[v] * x) / 128;
						vertexY[v] = (vertexY[v] * y) / 128;
						vertexZ[v] = (vertexZ[v] * z) / 128;
						vertexX[v] += baseX;
						vertexY[v] += baseY;
						vertexZ[v] += baseZ;
					}
				}
				break;
			case SeqSkeleton.OP_ALPHA:
				if ((labelFaces != null) && (faceAlpha != null)) {
					for (int label : labels) {
						if (label >= labelFaces.length) {
							continue;
						}
						int[] triangles = labelFaces[label];
						for (int t : triangles) {
							faceAlpha[t] += x * 8;
							if (faceAlpha[t] < 0) {
								faceAlpha[t] = 0;
							}
							if (faceAlpha[t] > 255) {
								faceAlpha[t] = 255;
							}
						}
					}
				}
				break;
		}
	}

	/**
	 * Rotates clockwise on the Y axis by 90 degrees.
	 */
	public void rotateY90() {
		for (int v = 0; v < vertexCount; v++) {
			int x_ = vertexX[v];
			vertexX[v] = vertexZ[v];
			vertexZ[v] = -x_;
		}
	}

	/**
	 * Rotates the model on the X/Pitch axis.
	 *
	 * @param angle the angle.
	 */
	public void rotateX(int angle) {
		int sin = Model.sin[angle];
		int cos = Model.cos[angle];
		for (int v = 0; v < vertexCount; v++) {
			int y_ = ((vertexY[v] * cos) - (vertexZ[v] * sin)) >> 16;
			vertexZ[v] = ((vertexY[v] * sin) + (vertexZ[v] * cos)) >> 16;
			vertexY[v] = y_;
		}
	}

	/**
	 * Translates the model by the offset provided.
	 *
	 * @param x the x.
	 * @param y the y.
	 * @param z the z.
	 */
	public void translate(int x, int y, int z) {
		for (int v = 0; v < vertexCount; v++) {
			vertexX[v] += x;
			vertexY[v] += y;
			vertexZ[v] += z;
		}
	}

	/**
	 * Replaces use of the color <code>src</code> with color <code>dst</code>.
	 *
	 * @param src the source texture id.
	 * @param dst the destination texture id.
	 */
	public void recolor(int src, int dst) {
		for (int k = 0; k < faceCount; k++) {
			if (faceColor[k] == src) {
				faceColor[k] = dst;
			}
		}
	}

	/**
	 * Performs the equivalent operation of rotating the entire model on the Y axis by 180 degrees.
	 */
	public void rotateY180() {
		for (int v = 0; v < vertexCount; v++) {
			vertexZ[v] = -vertexZ[v];
		}
		for (int f = 0; f < faceCount; f++) {
			int a = faceVertexA[f];
			faceVertexA[f] = faceVertexC[f];
			faceVertexC[f] = a;
		}
	}

	/**
	 * Scales the model. This method assumes 25.7 fixed-point integers, which means the value 1.0 will be <code>1<<7</code>
	 * or 128.
	 *
	 * @param x the x scalar.
	 * @param y the y scalar.
	 * @param z the z scalar.
	 */
	public void scale(int x, int y, int z) {
		for (int v = 0; v < vertexCount; v++) {
			vertexX[v] = (vertexX[v] * x) / 128;
			vertexY[v] = (vertexY[v] * z) / 128;
			vertexZ[v] = (vertexZ[v] * y) / 128;
		}
	}

	/**
	 * Recalculates the normals and optionally applies the lighting immediately after. This method <i>can</i> be
	 * destructive if <code>applyLighting</code> is set to <code>true</code>.
	 *
	 * @param lightAmbient     the ambient light.
	 * @param lightAttenuation the light attenuation.
	 * @param lightSrcX        the light source x.
	 * @param lightSrcY        the light source y.
	 * @param lightSrcZ        the light source z.
	 * @param applyLighting    <code>true</code> to invoke {@link #applyLighting(int, int, int, int, int)} after normals are
	 *                         calculated.
	 * @see #applyLighting(int, int, int, int, int)
	 */
	public void calculateNormals(int lightAmbient, int lightAttenuation, int lightSrcX, int lightSrcY, int lightSrcZ, boolean applyLighting) {
		int lightMagnitude = (int) Math.sqrt((lightSrcX * lightSrcX) + (lightSrcY * lightSrcY) + (lightSrcZ * lightSrcZ));
		int attenuation = (lightAttenuation * lightMagnitude) >> 8;

		if (faceColorA == null) {
			faceColorA = new int[faceCount];
			faceColorB = new int[faceCount];
			faceColorC = new int[faceCount];
		}

		if (super.vertexNormal == null) {
			super.vertexNormal = new VertexNormal[vertexCount];
			for (int v = 0; v < vertexCount; v++) {
				super.vertexNormal[v] = new VertexNormal();
			}
		}

		for (int f = 0; f < faceCount; f++) {
			int a = faceVertexA[f];
			int b = faceVertexB[f];
			int c = faceVertexC[f];

			int dxAB = vertexX[b] - vertexX[a];
			int dyAB = vertexY[b] - vertexY[a];
			int dzAB = vertexZ[b] - vertexZ[a];

			int dxAC = vertexX[c] - vertexX[a];
			int dyAC = vertexY[c] - vertexY[a];
			int dzAC = vertexZ[c] - vertexZ[a];

			int nx = (dyAB * dzAC) - (dyAC * dzAB);
			int ny = (dzAB * dxAC) - (dzAC * dxAB);
			int nz = (dxAB * dyAC) - (dxAC * dyAB);

			while ((nx > 8192) || (ny > 8192) || (nz > 8192) || (nx < -8192) || (ny < -8192) || (nz < -8192)) {
				nx >>= 1;
				ny >>= 1;
				nz >>= 1;
			}

			int length = (int) Math.sqrt((nx * nx) + (ny * ny) + (nz * nz));

			if (length <= 0) {
				length = 1;
			}

			// normalize
			nx = (nx * 256) / length;
			ny = (ny * 256) / length;
			nz = (nz * 256) / length;

			if ((faceInfo == null) || ((faceInfo[f] & 1) == 0)) {
				VertexNormal n = super.vertexNormal[a];
				n.x += nx;
				n.y += ny;
				n.z += nz;
				n.w++;
				n = super.vertexNormal[b];
				n.x += nx;
				n.y += ny;
				n.z += nz;
				n.w++;
				n = super.vertexNormal[c];
				n.x += nx;
				n.y += ny;
				n.z += nz;
				n.w++;
			} else {
				int lightness = lightAmbient + (((lightSrcX * nx) + (lightSrcY * ny) + (lightSrcZ * nz)) / (attenuation + (attenuation / 2)));
				faceColorA[f] = mulColorLightness(faceColor[f], lightness, faceInfo[f]);
			}
		}

		if (applyLighting) {
			applyLighting(lightAmbient, attenuation, lightSrcX, lightSrcY, lightSrcZ);
		} else {
			vertexNormalOriginal = new VertexNormal[vertexCount];

			for (int v = 0; v < vertexCount; v++) {
				VertexNormal normal = super.vertexNormal[v];
				VertexNormal copy = vertexNormalOriginal[v] = new VertexNormal();
				copy.x = normal.x;
				copy.y = normal.y;
				copy.z = normal.z;
				copy.w = normal.w;
			}
		}

		if (applyLighting) {
			calculateBoundsCylinder();
		} else {
			calculateBoundsAABB();
		}
	}

	/**
	 * Calculates the lightness values for all faces using the provided lighting parameters. This method is destructive
	 * and nullifies normals and labels, which means it is meant to be applied once to a model.
	 *
	 * @param lightAmbient     the ambient lighting value. [0...127]
	 * @param lightAttenuation the light attenuation.
	 * @param lightSrcX        the light source x.
	 * @param lightSrcY        the light source y.
	 * @param lightSrcZ        the light source z.
	 */
	public void applyLighting(int lightAmbient, int lightAttenuation, int lightSrcX, int lightSrcY, int lightSrcZ) {
		for (int f = 0; f < faceCount; f++) {
			int a = faceVertexA[f];
			int b = faceVertexB[f];
			int c = faceVertexC[f];

			if (faceInfo == null) {
				int color = faceColor[f];

				VertexNormal n = super.vertexNormal[a];
				int lightness = lightAmbient + (((lightSrcX * n.x) + (lightSrcY * n.y) + (lightSrcZ * n.z)) / (lightAttenuation * n.w));
				faceColorA[f] = mulColorLightness(color, lightness, 0);

				n = super.vertexNormal[b];
				lightness = lightAmbient + (((lightSrcX * n.x) + (lightSrcY * n.y) + (lightSrcZ * n.z)) / (lightAttenuation * n.w));
				faceColorB[f] = mulColorLightness(color, lightness, 0);

				n = super.vertexNormal[c];
				lightness = lightAmbient + (((lightSrcX * n.x) + (lightSrcY * n.y) + (lightSrcZ * n.z)) / (lightAttenuation * n.w));
				faceColorC[f] = mulColorLightness(color, lightness, 0);
			} else if ((faceInfo[f] & 1) == 0) {
				int color = faceColor[f];
				int info = faceInfo[f];

				VertexNormal n = super.vertexNormal[a];
				int lightness = lightAmbient + (((lightSrcX * n.x) + (lightSrcY * n.y) + (lightSrcZ * n.z)) / (lightAttenuation * n.w));
				faceColorA[f] = mulColorLightness(color, lightness, info);

				n = super.vertexNormal[b];
				lightness = lightAmbient + (((lightSrcX * n.x) + (lightSrcY * n.y) + (lightSrcZ * n.z)) / (lightAttenuation * n.w));
				faceColorB[f] = mulColorLightness(color, lightness, info);

				n = super.vertexNormal[c];
				lightness = lightAmbient + (((lightSrcX * n.x) + (lightSrcY * n.y) + (lightSrcZ * n.z)) / (lightAttenuation * n.w));
				faceColorC[f] = mulColorLightness(color, lightness, info);
			}
		}

		super.vertexNormal = null;
		vertexNormalOriginal = null;
		vertexLabel = null;
		faceLabel = null;

		if (faceInfo != null) {
			for (int f = 0; f < faceCount; f++) {
				if ((faceInfo[f] & 2) == 2) {
					return;
				}
			}
		}

		faceColor = null;
	}

	/**
	 * Draws this model with reduced parameters.
	 *
	 * @param pitch    the model pitch.
	 * @param yaw      the model yaw.
	 * @param roll     the model roll.
	 * @param eyePitch the eye pitch.
	 * @param eyeX     the eye x.
	 * @param eyeY     the eye y.
	 * @param eyeZ     the eye z.
	 */
	public void drawSimple(int pitch, int yaw, int roll, int eyePitch, int eyeX, int eyeY, int eyeZ) {
		int centerX = Draw3D.centerX;
		int centerY = Draw3D.centerY;
		int sinPitch = sin[pitch];
		int cosPitch = cos[pitch];
		int sinYaw = sin[yaw];
		int cosYaw = cos[yaw];
		int sinRoll = sin[roll];
		int cosRoll = cos[roll];
		int sinEyePitch = sin[eyePitch];
		int cosEyePitch = cos[eyePitch];
		int midZ = ((eyeY * sinEyePitch) + (eyeZ * cosEyePitch)) >> 16;

		for (int v = 0; v < vertexCount; v++) {
			int x = vertexX[v];
			int y = vertexY[v];
			int z = vertexZ[v];

			// Local Space -> Model Space

			if (roll != 0) {
				int x_ = ((y * sinRoll) + (x * cosRoll)) >> 16;
				y = ((y * cosRoll) - (x * sinRoll)) >> 16;
				x = x_;
			}

			if (pitch != 0) {
				int y_ = ((y * cosPitch) - (z * sinPitch)) >> 16;
				z = ((y * sinPitch) + (z * cosPitch)) >> 16;
				y = y_;
			}

			if (yaw != 0) {
				int x_ = ((z * sinYaw) + (x * cosYaw)) >> 16;
				z = ((z * cosYaw) - (x * sinYaw)) >> 16;
				x = x_;
			}

			// Model Space -> View Space

			x += eyeX;
			y += eyeY;
			z += eyeZ;

			int y_ = ((y * cosEyePitch) - (z * sinEyePitch)) >> 16;
			z = ((y * sinEyePitch) + (z * cosEyePitch)) >> 16;
			y = y_;

			// View Space -> Screen Space

			vertexScreenX[v] = centerX + ((x << 9) / z);
			vertexScreenY[v] = centerY + ((y << 9) / z);
			vertexScreenZ[v] = z - midZ;

			// Store viewspace coordinates to be transformed into screen space later (textured or clipped triangles)

			if (texturedFaceCount > 0) {
				vertexViewSpaceX[v] = x;
				vertexViewSpaceY[v] = y;
				vertexViewSpaceZ[v] = z;
			}
		}
		try {
			draw(false, false, 0);
		} catch (Exception ignored) {
		}
	}

	/**
	 * Draws this model.
	 *
	 * @param yaw         the yaw of this model.
	 * @param sinEyePitch the sin(eyePitch).
	 * @param cosEyePitch the cos(eyePitch).
	 * @param sinEyeYaw   the sin(eyeYaw).
	 * @param cosEyeYaw   the cos(eyeYaw).
	 * @param relativeX   the relative x. (SceneX - EyeX)
	 * @param relativeY   the relative y. (SceneY - EyeY)
	 * @param relativeZ   the relative z. (SceneZ - EyeZ)
	 * @param bitset      the bitset.
	 */
	@Override
	public void draw(int yaw, int sinEyePitch, int cosEyePitch, int sinEyeYaw, int cosEyeYaw, int relativeX, int relativeY, int relativeZ, int bitset) {
		// Relative coordinates are ScenePos - EyePos

		// z' is our relative z value rotated by eye yaw.
		int zPrime = ((relativeZ * cosEyeYaw) - (relativeX * sinEyeYaw)) >> 16;

		// midZ is our relative z value rotated by eye yaw and pitch. It's the distance from the camera from the center
		// of our model.
		int midZ = ((relativeY * sinEyePitch) + (zPrime * cosEyePitch)) >> 16;

		// Our pitch is clamped between 128 and 384 (22.5 degrees and 67.5 degrees)
		// We know this will be positive and within 92->38% its original value.
		int radiusCosEyePitch = (radius * cosEyePitch) >> 16;

		// +Z goes forward, which makes this value supposedly the farthest Z the model should be away from the camera.
		int maxZ = midZ + radiusCosEyePitch;

		// early z testing
		if ((maxZ <= 50) || (midZ >= 3500)) {
			return;
		}

		// calculate x'
		int midX = ((relativeZ * sinEyeYaw) + (relativeX * cosEyeYaw)) >> 16;

		// calculate left bound
		int leftX = (midX - radius) << 9;

		// early fail
		if ((leftX / maxZ) >= Draw2D.centerX) {
			return;
		}

		// calculate right bound
		int rightX = (midX + radius) << 9;

		// early fail
		if ((rightX / maxZ) <= -Draw2D.centerX) {
			return;
		}

		// midY is our relative y value rotated by eye pitch
		int midY = ((relativeY * cosEyePitch) - (zPrime * sinEyePitch)) >> 16;

		// Our pitch is clamped between 128 and 384 (22.5 degrees and 67.5 degrees)
		// We know this will be positive and within 38->92% its original value.
		int radiusSinEyePitch = (radius * sinEyePitch) >> 16;

		// calculate bottom bound
		int bottomY = (midY + radiusSinEyePitch) << 9;

		// early fail
		if ((bottomY / maxZ) <= -Draw2D.centerY) {
			return;
		}

		// y' = (radius * sin(eyePitch)) + (minY * cos(eyePitch))
		int yPrime = radiusSinEyePitch + ((super.minY * cosEyePitch) >> 16);

		// calculate top boundary
		int topY = (midY - yPrime) << 9;

		// early fail
		if ((topY / maxZ) >= Draw2D.centerY) {
			return;
		}

		// (minY * sin(eyePitch)) + (radius * cos(eyePitch))
		int radiusZ = ((super.minY * sinEyePitch) >> 16) + radiusCosEyePitch;

		boolean clipped = (midZ - radiusZ) <= 50;
		boolean picking = false;

		if ((bitset > 0) && Model.checkHover) {
			int z = midZ - radiusCosEyePitch;

			if (z <= 50) {
				z = 50;
			}

			if (midX > 0) {
				leftX /= maxZ;
				rightX /= z;
			} else {
				rightX /= maxZ;
				leftX /= z;
			}

			if (midY > 0) {
				topY /= maxZ;
				bottomY /= z;
			} else {
				bottomY /= maxZ;
				topY /= z;
			}

			int mouseX = Model.mouseX - Draw3D.centerX;
			int mouseY = Model.mouseY - Draw3D.centerY;

			if ((mouseX > leftX) && (mouseX < rightX) && (mouseY > topY) && (mouseY < bottomY)) {
				if (pickable) {
					pickedBitsets[pickedCount++] = bitset;
				} else {
					picking = true;
				}
			}
		}

		int centerX = Draw3D.centerX;
		int centerY = Draw3D.centerY;
		int sinYaw = 0;
		int cosYaw = 0;

		if (yaw != 0) {
			sinYaw = sin[yaw];
			cosYaw = cos[yaw];
		}

		for (int v = 0; v < vertexCount; v++) {
			int x = vertexX[v];
			int y = vertexY[v];
			int z = vertexZ[v];

			// Local Space -> Model Space

			if (yaw != 0) {
				int x_ = ((z * sinYaw) + (x * cosYaw)) >> 16;
				z = ((z * cosYaw) - (x * sinYaw)) >> 16;
				x = x_;
			}

			// Model Space -> View Space

			x += relativeX;
			y += relativeY;
			z += relativeZ;

			// Rotate on Y axis (Yaw)
			int tmp = ((z * sinEyeYaw) + (x * cosEyeYaw)) >> 16;
			z = ((z * cosEyeYaw) - (x * sinEyeYaw)) >> 16;
			x = tmp;

			// Rotate on X axis (Pitch)
			tmp = ((y * cosEyePitch) - (z * sinEyePitch)) >> 16;
			z = ((y * sinEyePitch) + (z * cosEyePitch)) >> 16;
			y = tmp;

			if (z >= 50) {
				vertexScreenX[v] = centerX + ((x << 9) / z);
				vertexScreenY[v] = centerY + ((y << 9) / z);
			} else {
				vertexScreenX[v] = -5000; // used in drawTriangle to denote a near-clipped triangle.
				clipped = true;
			}

			vertexScreenZ[v] = z - midZ;

			if (clipped || (texturedFaceCount > 0)) {
				vertexViewSpaceX[v] = x;
				vertexViewSpaceY[v] = y;
				vertexViewSpaceZ[v] = z;
			}
		}
		try {
			draw(clipped, picking, bitset);
		} catch (Exception ignored) {
		}
	}

	/**
	 * Draws the {@link Model} with the provided parameters. This method performs depth and priority sorting.
	 *
	 * @param clipped whether to check near plane clipping.
	 * @param picking <code>true</code> to enable picking.
	 * @param bitset  the bitset. Used with <code>pick</code> set to true.
	 */
	public void draw(boolean clipped, boolean picking, int bitset) {
		for (int depth = 0; depth < maxDepth; depth++) {
			tmpDepthFaceCount[depth] = 0;
		}

		for (int f = 0; f < faceCount; f++) {
			if ((faceInfo != null) && (faceInfo[f] == -1)) {
				continue;
			}

			int a = faceVertexA[f];
			int b = faceVertexB[f];
			int c = faceVertexC[f];
			int xA = vertexScreenX[a];
			int xB = vertexScreenX[b];
			int xC = vertexScreenX[c];

			if (clipped && ((xA == -5000) || (xB == -5000) || (xC == -5000))) {
				faceNearClipped[f] = true;
				int depthAverage = ((vertexScreenZ[a] + vertexScreenZ[b] + vertexScreenZ[c]) / 3) + minDepth;
				tmpDepthFaces[depthAverage][tmpDepthFaceCount[depthAverage]++] = f;
			} else {
				if (picking && pointWithinTriangle(mouseX, mouseY, vertexScreenY[a], vertexScreenY[b], vertexScreenY[c], xA, xB, xC)) {
					pickedBitsets[pickedCount++] = bitset;
					picking = false;
				}

				// Back-face culling
				int dxAB = xA - xB;
				int dyAB = vertexScreenY[a] - vertexScreenY[b];
				int dxCB = xC - xB;
				int dyCB = vertexScreenY[c] - vertexScreenY[b];

				if (((dxAB * dyCB) - (dyAB * dxCB)) <= 0) {
					continue;
				}

				faceNearClipped[f] = false;
				faceClippedX[f] = (xA < 0) || (xB < 0) || (xC < 0) || (xA > Draw2D.boundX) || (xB > Draw2D.boundX) || (xC > Draw2D.boundX);

				int depthAverage = ((vertexScreenZ[a] + vertexScreenZ[b] + vertexScreenZ[c]) / 3) + minDepth;
				tmpDepthFaces[depthAverage][tmpDepthFaceCount[depthAverage]++] = f;
			}
		}

		if (facePriority == null) {
			for (int depth = maxDepth - 1; depth >= 0; depth--) {
				int count = tmpDepthFaceCount[depth];
				if (count > 0) {
					int[] faces = tmpDepthFaces[depth];
					for (int f = 0; f < count; f++) {
						drawFace(faces[f]);
					}
				}
			}
			return;
		}

		for (int priority = 0; priority < 12; priority++) {
			Model.tmpPriorityFaceCount[priority] = 0;
			tmpPriorityDepthSum[priority] = 0;
		}

		for (int depth = maxDepth - 1; depth >= 0; depth--) {
			int faceCount = tmpDepthFaceCount[depth];

			if (faceCount <= 0) {
				continue;
			}

			int[] faces = tmpDepthFaces[depth];

			for (int i = 0; i < faceCount; i++) {
				int face = faces[i];
				int priority = facePriority[face];
				int count = Model.tmpPriorityFaceCount[priority]++;
				Model.tmpPriorityFaces[priority][count] = face;

				if (priority < 10) {
					tmpPriorityDepthSum[priority] += depth;
				} else if (priority == 10) {
					tmpPriority10FaceDepth[count] = depth;
				} else {
					tmpPriority11FaceDepth[count] = depth;
				}
			}
		}

		// These are as the name implies, the average depth between two priorities.
		int averagePriorityDepth1_2 = 0;
		int averagePriorityDepth3_4 = 0;
		int averagePriorityDepth6_8 = 0;

		// Don't think too hard about it. It's just a way of calculating averages with integers but with two sums averaged together.

		if ((Model.tmpPriorityFaceCount[1] > 0) || (Model.tmpPriorityFaceCount[2] > 0)) {
			averagePriorityDepth1_2 = (tmpPriorityDepthSum[1] + tmpPriorityDepthSum[2]) / (Model.tmpPriorityFaceCount[1] + Model.tmpPriorityFaceCount[2]);
		}

		if ((Model.tmpPriorityFaceCount[3] > 0) || (Model.tmpPriorityFaceCount[4] > 0)) {
			averagePriorityDepth3_4 = (tmpPriorityDepthSum[3] + tmpPriorityDepthSum[4]) / (Model.tmpPriorityFaceCount[3] + Model.tmpPriorityFaceCount[4]);
		}

		if ((Model.tmpPriorityFaceCount[6] > 0) || (Model.tmpPriorityFaceCount[8] > 0)) {
			averagePriorityDepth6_8 = (tmpPriorityDepthSum[6] + tmpPriorityDepthSum[8]) / (Model.tmpPriorityFaceCount[6] + Model.tmpPriorityFaceCount[8]);
		}

		int priorityFace = 0;
		int priorityFaceCount = Model.tmpPriorityFaceCount[10];
		int[] priorityFaces = Model.tmpPriorityFaces[10];
		int[] priorityFaceDepths = tmpPriority10FaceDepth;

		if (priorityFace == priorityFaceCount) {
			priorityFace = 0;
			priorityFaceCount = Model.tmpPriorityFaceCount[11];
			priorityFaces = Model.tmpPriorityFaces[11];
			priorityFaceDepths = tmpPriority11FaceDepth;
		}

		int priorityDepth;
		if (priorityFace < priorityFaceCount) {
			priorityDepth = priorityFaceDepths[priorityFace];
		} else {
			priorityDepth = -1000;
		}

		// The code below essentially gives priorities 10 and 11 a chance to draw during priorities 0, 3, and 5 as long
		// as the current face depth is slightly deeper than the current priority depth. You can think of that slightly
		// deeper concept as a threshold to allow lower priority triangles to draw on top of higher priority triangles.

		// If they didn't do this then higher priority triangles would always draw on top, which look weird.

		for (int priority = 0; priority < 10; priority++) {
			while ((priority == 0) && (priorityDepth > averagePriorityDepth1_2)) {
				drawFace(priorityFaces[priorityFace++]);

				if ((priorityFace == priorityFaceCount) && (priorityFaces != Model.tmpPriorityFaces[11])) {
					priorityFace = 0;
					priorityFaceCount = Model.tmpPriorityFaceCount[11];
					priorityFaces = Model.tmpPriorityFaces[11];
					priorityFaceDepths = tmpPriority11FaceDepth;
				}

				if (priorityFace < priorityFaceCount) {
					priorityDepth = priorityFaceDepths[priorityFace];
				} else {
					priorityDepth = -1000;
				}
			}

			while ((priority == 3) && (priorityDepth > averagePriorityDepth3_4)) {
				drawFace(priorityFaces[priorityFace++]);

				if ((priorityFace == priorityFaceCount) && (priorityFaces != Model.tmpPriorityFaces[11])) {
					priorityFace = 0;
					priorityFaceCount = Model.tmpPriorityFaceCount[11];
					priorityFaces = Model.tmpPriorityFaces[11];
					priorityFaceDepths = tmpPriority11FaceDepth;
				}

				if (priorityFace < priorityFaceCount) {
					priorityDepth = priorityFaceDepths[priorityFace];
				} else {
					priorityDepth = -1000;
				}
			}

			while ((priority == 5) && (priorityDepth > averagePriorityDepth6_8)) {
				drawFace(priorityFaces[priorityFace++]);

				if ((priorityFace == priorityFaceCount) && (priorityFaces != Model.tmpPriorityFaces[11])) {
					priorityFace = 0;
					priorityFaceCount = Model.tmpPriorityFaceCount[11];
					priorityFaces = Model.tmpPriorityFaces[11];
					priorityFaceDepths = tmpPriority11FaceDepth;
				}

				if (priorityFace < priorityFaceCount) {
					priorityDepth = priorityFaceDepths[priorityFace];
				} else {
					priorityDepth = -1000;
				}
			}

			int count = Model.tmpPriorityFaceCount[priority];
			int[] faces = Model.tmpPriorityFaces[priority];

			for (int i = 0; i < count; i++) {
				drawFace(faces[i]);
			}
		}

		// finish off remaining faces (priorities 10 and 11)

		while (priorityDepth != -1000) {
			drawFace(priorityFaces[priorityFace++]);

			if ((priorityFace == priorityFaceCount) && (priorityFaces != Model.tmpPriorityFaces[11])) {
				priorityFace = 0;
				priorityFaces = Model.tmpPriorityFaces[11];
				priorityFaceCount = Model.tmpPriorityFaceCount[11];
				priorityFaceDepths = tmpPriority11FaceDepth;
			}

			if (priorityFace < priorityFaceCount) {
				priorityDepth = priorityFaceDepths[priorityFace];
			} else {
				priorityDepth = -1000;
			}
		}
	}

	/**
	 * Draws the provided face id.
	 *
	 * @param face the face id.
	 */
	public void drawFace(int face) {
		if (faceNearClipped[face]) {
			drawNearClippedFace(face);
			return;
		}

		int a = faceVertexA[face];
		int b = faceVertexB[face];
		int c = faceVertexC[face];
		Draw3D.clipX = faceClippedX[face];

		if (faceAlpha == null) {
			Draw3D.alpha = 0;
		} else {
			Draw3D.alpha = faceAlpha[face];
		}

		int type;

		if (faceInfo == null) {
			type = 0;
		} else {
			type = faceInfo[face] & 0b11;
		}

		if (type == 0) {
			Draw3D.fillGouraudTriangle(vertexScreenY[a], vertexScreenY[b], vertexScreenY[c], vertexScreenX[a], vertexScreenX[b], vertexScreenX[c], faceColorA[face], faceColorB[face], faceColorC[face]);
		} else if (type == 1) {
			Draw3D.fillTriangle(vertexScreenY[a], vertexScreenY[b], vertexScreenY[c], vertexScreenX[a], vertexScreenX[b], vertexScreenX[c], palette[faceColorA[face]]);
		} else if (type == 2) {
			int texturedFace = faceInfo[face] >> 2;
			int ta = texturedVertexA[texturedFace];
			int tb = texturedVertexB[texturedFace];
			int tc = texturedVertexC[texturedFace];
			Draw3D.fillTexturedTriangle(vertexScreenY[a], vertexScreenY[b], vertexScreenY[c], vertexScreenX[a], vertexScreenX[b], vertexScreenX[c], faceColorA[face], faceColorB[face], faceColorC[face], vertexViewSpaceX[ta], vertexViewSpaceX[tb], vertexViewSpaceX[tc], vertexViewSpaceY[ta], vertexViewSpaceY[tb], vertexViewSpaceY[tc], vertexViewSpaceZ[ta], vertexViewSpaceZ[tb], vertexViewSpaceZ[tc], faceColor[face]);
		} else if (type == 3) {
			int texturedFace = faceInfo[face] >> 2;
			int ta = texturedVertexA[texturedFace];
			int tb = texturedVertexB[texturedFace];
			int tc = texturedVertexC[texturedFace];
			Draw3D.fillTexturedTriangle(vertexScreenY[a], vertexScreenY[b], vertexScreenY[c], vertexScreenX[a], vertexScreenX[b], vertexScreenX[c], faceColorA[face], faceColorA[face], faceColorA[face], vertexViewSpaceX[ta], vertexViewSpaceX[tb], vertexViewSpaceX[tc], vertexViewSpaceY[ta], vertexViewSpaceY[tb], vertexViewSpaceY[tc], vertexViewSpaceZ[ta], vertexViewSpaceZ[tb], vertexViewSpaceZ[tc], faceColor[face]);
		}
	}

	/**
	 * Draws the provided face id assuming it has been clipped by the near Z plane.
	 *
	 * @param face the face id.
	 */
	public void drawNearClippedFace(int face) {
		int centerX = Draw3D.centerX;
		int centerY = Draw3D.centerY;
		int elements = 0;

		int a = faceVertexA[face];
		int b = faceVertexB[face];
		int c = faceVertexC[face];

		int zA = vertexViewSpaceZ[a];
		int zB = vertexViewSpaceZ[b];
		int zC = vertexViewSpaceZ[c];

		if (zA >= 50) {
			clippedX[elements] = vertexScreenX[a];
			clippedY[elements] = vertexScreenY[a];
			clippedColor[elements++] = faceColorA[face];
		} else {
			int xA = vertexViewSpaceX[a];
			int yA = vertexViewSpaceY[a];
			int colorA = faceColorA[face];

			if (zC >= 50) {
				int scalar = (50 - zA) * reciprical16[zC - zA];
				clippedX[elements] = centerX + (((xA + (((vertexViewSpaceX[c] - xA) * scalar) >> 16)) << 9) / 50);
				clippedY[elements] = centerY + (((yA + (((vertexViewSpaceY[c] - yA) * scalar) >> 16)) << 9) / 50);
				clippedColor[elements++] = colorA + (((faceColorC[face] - colorA) * scalar) >> 16);
			}

			if (zB >= 50) {
				int scalar = (50 - zA) * reciprical16[zB - zA];
				clippedX[elements] = centerX + (((xA + (((vertexViewSpaceX[b] - xA) * scalar) >> 16)) << 9) / 50);
				clippedY[elements] = centerY + (((yA + (((vertexViewSpaceY[b] - yA) * scalar) >> 16)) << 9) / 50);
				clippedColor[elements++] = colorA + (((faceColorB[face] - colorA) * scalar) >> 16);
			}
		}

		if (zB >= 50) {
			clippedX[elements] = vertexScreenX[b];
			clippedY[elements] = vertexScreenY[b];
			clippedColor[elements++] = faceColorB[face];
		} else {
			int xB = vertexViewSpaceX[b];
			int yB = vertexViewSpaceY[b];
			int colorB = faceColorB[face];

			if (zA >= 50) {
				int scalar = (50 - zB) * reciprical16[zA - zB];
				clippedX[elements] = centerX + (((xB + (((vertexViewSpaceX[a] - xB) * scalar) >> 16)) << 9) / 50);
				clippedY[elements] = centerY + (((yB + (((vertexViewSpaceY[a] - yB) * scalar) >> 16)) << 9) / 50);
				clippedColor[elements++] = colorB + (((faceColorA[face] - colorB) * scalar) >> 16);
			}

			if (zC >= 50) {
				int scalar = (50 - zB) * reciprical16[zC - zB];
				clippedX[elements] = centerX + (((xB + (((vertexViewSpaceX[c] - xB) * scalar) >> 16)) << 9) / 50);
				clippedY[elements] = centerY + (((yB + (((vertexViewSpaceY[c] - yB) * scalar) >> 16)) << 9) / 50);
				clippedColor[elements++] = colorB + (((faceColorC[face] - colorB) * scalar) >> 16);
			}
		}

		if (zC >= 50) {
			clippedX[elements] = vertexScreenX[c];
			clippedY[elements] = vertexScreenY[c];
			clippedColor[elements++] = faceColorC[face];
		} else {
			int xC = vertexViewSpaceX[c];
			int yC = vertexViewSpaceY[c];
			int colorC = faceColorC[face];

			if (zB >= 50) {
				int k6 = (50 - zC) * reciprical16[zB - zC];
				clippedX[elements] = centerX + (((xC + (((vertexViewSpaceX[b] - xC) * k6) >> 16)) << 9) / 50);
				clippedY[elements] = centerY + (((yC + (((vertexViewSpaceY[b] - yC) * k6) >> 16)) << 9) / 50);
				clippedColor[elements++] = colorC + (((faceColorB[face] - colorC) * k6) >> 16);
			}

			if (zA >= 50) {
				int l6 = (50 - zC) * reciprical16[zA - zC];
				clippedX[elements] = centerX + (((xC + (((vertexViewSpaceX[a] - xC) * l6) >> 16)) << 9) / 50);
				clippedY[elements] = centerY + (((yC + (((vertexViewSpaceY[a] - yC) * l6) >> 16)) << 9) / 50);
				clippedColor[elements++] = colorC + (((faceColorA[face] - colorC) * l6) >> 16);
			}
		}

		int x0 = clippedX[0];
		int x1 = clippedX[1];
		int x2 = clippedX[2];
		int y0 = clippedY[0];
		int y1 = clippedY[1];
		int y2 = clippedY[2];

		// Back-face culling
		if ((((x0 - x1) * (y2 - y1)) - ((y0 - y1) * (x2 - x1))) <= 0) {
			return;
		}

		Draw3D.clipX = false;

		// It's possible for a single triangle to be clipped into two separate triangles.

		if (elements == 3) {
			if ((x0 < 0) || (x1 < 0) || (x2 < 0) || (x0 > Draw2D.boundX) || (x1 > Draw2D.boundX) || (x2 > Draw2D.boundX)) {
				Draw3D.clipX = true;
			}

			int type;

			if (faceInfo == null) {
				type = 0;
			} else {
				type = faceInfo[face] & 3;
			}

			if (type == 0) {
				Draw3D.fillGouraudTriangle(y0, y1, y2, x0, x1, x2, clippedColor[0], clippedColor[1], clippedColor[2]);
			} else if (type == 1) {
				Draw3D.fillTriangle(y0, y1, y2, x0, x1, x2, palette[faceColorA[face]]);
			} else if (type == 2) {
				int texturedFace = faceInfo[face] >> 2;
				int tA = texturedVertexA[texturedFace];
				int tB = texturedVertexB[texturedFace];
				int tC = texturedVertexC[texturedFace];
				Draw3D.fillTexturedTriangle(y0, y1, y2, x0, x1, x2, clippedColor[0], clippedColor[1], clippedColor[2], vertexViewSpaceX[tA], vertexViewSpaceX[tB], vertexViewSpaceX[tC], vertexViewSpaceY[tA], vertexViewSpaceY[tB], vertexViewSpaceY[tC], vertexViewSpaceZ[tA], vertexViewSpaceZ[tB], vertexViewSpaceZ[tC], faceColor[face]);
			} else if (type == 3) {
				int texturedFace = faceInfo[face] >> 2;
				int tA = texturedVertexA[texturedFace];
				int tB = texturedVertexB[texturedFace];
				int tC = texturedVertexC[texturedFace];
				Draw3D.fillTexturedTriangle(y0, y1, y2, x0, x1, x2, faceColorA[face], faceColorA[face], faceColorA[face], vertexViewSpaceX[tA], vertexViewSpaceX[tB], vertexViewSpaceX[tC], vertexViewSpaceY[tA], vertexViewSpaceY[tB], vertexViewSpaceY[tC], vertexViewSpaceZ[tA], vertexViewSpaceZ[tB], vertexViewSpaceZ[tC], faceColor[face]);
			}
		} else if (elements == 4) {
			if ((x0 < 0) || (x1 < 0) || (x2 < 0) || (x0 > Draw2D.boundX) || (x1 > Draw2D.boundX) || (x2 > Draw2D.boundX) || (clippedX[3] < 0) || (clippedX[3] > Draw2D.boundX)) {
				Draw3D.clipX = true;
			}

			int type;

			if (faceInfo == null) {
				type = 0;
			} else {
				type = faceInfo[face] & 3;
			}

			if (type == 0) {
				Draw3D.fillGouraudTriangle(y0, y1, y2, x0, x1, x2, clippedColor[0], clippedColor[1], clippedColor[2]);
				Draw3D.fillGouraudTriangle(y0, y2, clippedY[3], x0, x2, clippedX[3], clippedColor[0], clippedColor[2], clippedColor[3]);
			} else if (type == 1) {
				int colorA = palette[faceColorA[face]];
				Draw3D.fillTriangle(y0, y1, y2, x0, x1, x2, colorA);
				Draw3D.fillTriangle(y0, y2, clippedY[3], x0, x2, clippedX[3], colorA);
			} else if (type == 2) {
				int texturedFace = faceInfo[face] >> 2;
				int tA = texturedVertexA[texturedFace];
				int tB = texturedVertexB[texturedFace];
				int tC = texturedVertexC[texturedFace];
				Draw3D.fillTexturedTriangle(y0, y1, y2, x0, x1, x2, clippedColor[0], clippedColor[1], clippedColor[2], vertexViewSpaceX[tA], vertexViewSpaceX[tB], vertexViewSpaceX[tC], vertexViewSpaceY[tA], vertexViewSpaceY[tB], vertexViewSpaceY[tC], vertexViewSpaceZ[tA], vertexViewSpaceZ[tB], vertexViewSpaceZ[tC], faceColor[face]);
				Draw3D.fillTexturedTriangle(y0, y2, clippedY[3], x0, x2, clippedX[3], clippedColor[0], clippedColor[2], clippedColor[3], vertexViewSpaceX[tA], vertexViewSpaceX[tB], vertexViewSpaceX[tC], vertexViewSpaceY[tA], vertexViewSpaceY[tB], vertexViewSpaceY[tC], vertexViewSpaceZ[tA], vertexViewSpaceZ[tB], vertexViewSpaceZ[tC], faceColor[face]);
			} else if (type == 3) {
				int texturedFace = faceInfo[face] >> 2;
				int tA = texturedVertexA[texturedFace];
				int tB = texturedVertexB[texturedFace];
				int tC = texturedVertexC[texturedFace];
				Draw3D.fillTexturedTriangle(y0, y1, y2, x0, x1, x2, faceColorA[face], faceColorA[face], faceColorA[face], vertexViewSpaceX[tA], vertexViewSpaceX[tB], vertexViewSpaceX[tC], vertexViewSpaceY[tA], vertexViewSpaceY[tB], vertexViewSpaceY[tC], vertexViewSpaceZ[tA], vertexViewSpaceZ[tB], vertexViewSpaceZ[tC], faceColor[face]);
				Draw3D.fillTexturedTriangle(y0, y2, clippedY[3], x0, x2, clippedX[3], faceColorA[face], faceColorA[face], faceColorA[face], vertexViewSpaceX[tA], vertexViewSpaceX[tB], vertexViewSpaceX[tC], vertexViewSpaceY[tA], vertexViewSpaceY[tB], vertexViewSpaceY[tC], vertexViewSpaceZ[tA], vertexViewSpaceZ[tB], vertexViewSpaceZ[tC], faceColor[face]);
			}
		}
	}

	/**
	 * Utility function. Checks if <code>(x, y)</code> is within the provided triangle.
	 *
	 * @param x  the x.
	 * @param y  the y.
	 * @param yA y of corner a.
	 * @param yB y of corner b.
	 * @param yC y of corner c.
	 * @param xA x of corner a.
	 * @param xB x of corner b.
	 * @param xC x of corner c.
	 * @return <code>true</code> if <code>(x, y)</code> is within the triangle.
	 */
	public boolean pointWithinTriangle(int x, int y, int yA, int yB, int yC, int xA, int xB, int xC) {
		if ((y < yA) && (y < yB) && (y < yC)) {
			return false;
		}
		if ((y > yA) && (y > yB) && (y > yC)) {
			return false;
		}
		if ((x < xA) && (x < xB) && (x < xC)) {
			return false;
		}
		return (x <= xA) || (x <= xB) || (x <= xC);
	}

}
