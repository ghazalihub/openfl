package openfl.display3D

enum class Context3DBufferUsage {
    DYNAMIC_DRAW,
    STATIC_DRAW
}

enum class Context3DCompareMode {
    ALWAYS,
    EQUAL,
    GREATER,
    GREATER_EQUAL,
    LESS,
    LESS_EQUAL,
    NEVER,
    NOT_EQUAL
}

enum class Context3DMipFilter {
    MIPLINEAR,
    MIPNEAREST,
    MIPNONE
}

enum class Context3DProfile {
    BASELINE,
    BASELINE_CONSTRAINED,
    BASELINE_EXTENDED,
    ENHANCED,
    STANDARD,
    STANDARD_CONSTRAINED,
    STANDARD_EXTENDED
}

enum class Context3DProgramFormat {
    AGAL,
    GLSL
}

enum class Context3DProgramType {
    FRAGMENT,
    VERTEX
}

enum class Context3DRenderMode {
    AUTO,
    SOFTWARE
}

enum class Context3DStencilAction {
    DECREMENT_SATURATE,
    DECREMENT_WRAP,
    INCREMENT_SATURATE,
    INCREMENT_WRAP,
    INVERT,
    KEEP,
    SET,
    ZERO
}

enum class Context3DTextureFilter {
    ANISOTROPIC16X,
    ANISOTROPIC2X,
    ANISOTROPIC4X,
    ANISOTROPIC8X,
    LINEAR,
    NEAREST
}

enum class Context3DTextureFormat {
    BGR_PACKED,
    BGRA,
    BGRA_PACKED,
    COMPRESSED,
    COMPRESSED_ALPHA,
    RGBA,
    RGBA_HALF_FLOAT
}

enum class Context3DTriangleFace {
    BACK,
    FRONT,
    FRONT_AND_BACK,
    NONE
}

enum class Context3DVertexBufferFormat {
    BYTES_4,
    FLOAT_1,
    FLOAT_2,
    FLOAT_3,
    FLOAT_4
}

enum class Context3DWrapMode {
    CLAMP,
    CLAMP_U_REPEAT_V,
    REPEAT,
    REPEAT_U_CLAMP_V
}
