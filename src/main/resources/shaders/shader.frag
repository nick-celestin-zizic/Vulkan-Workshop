#version 450
#extension GL_ARB_separate_shader_objects : enable

layout(set = 0, binding = 1) uniform sampler2DArray texSampler;
layout(location = 0)         in      vec4 fragColor;
layout(location = 1)         in      vec3 fragTexCoord;
layout(location = 2)         in      vec3 userData;
layout(location = 0)         out     vec4 outColor;

void main() {
    vec4 texColor = texture(texSampler, fragTexCoord);
    if (texColor.w < 1.0) discard;
    outColor = texColor + fragColor;
}