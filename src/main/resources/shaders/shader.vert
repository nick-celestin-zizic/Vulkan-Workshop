#version 450
#extension GL_ARB_separate_shader_objects : enable

layout(set = 0, binding = 0) uniform UniformBufferObject {
    mat4 view;
    mat4 proj;
    mat4 models[128];
    vec4 textureIds[128];
} ubo;

layout(location = 0) in  vec4 inColor;
layout(location = 0) out vec4 fragColor;
layout(location = 1) out vec3 fragTexCoord;

vec2 positions[4] = vec2[]
  (
   vec2(-0.5, -0.5),
   vec2( 0.5, -0.5),
   vec2( 0.5,  0.5),
   vec2(-0.5,  0.5)
  );

vec2 uvs[4] = vec2[]
  (
    vec2(1.0, 0.0),
    vec2(0.0, 0.0),
    vec2(0.0, 1.0),
    vec2(1.0, 1.0)
  );


uint indices[6] = uint[]
  (
    0, 1, 2,
    0, 2, 3
  );

void main() {
    uint index   = indices[gl_VertexIndex];
    gl_Position  = ubo.proj * ubo.view * ubo.models[gl_InstanceIndex] * vec4(positions[index], 0.0, 1.0);
    fragTexCoord = vec3(uvs[index], ubo.textureIds[gl_InstanceIndex].x);
    fragColor    = inColor;
}
