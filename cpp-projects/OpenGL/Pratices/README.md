# OpenGL Projects Collection

This repository showcases several foundational OpenGL programs built using modern OpenGL (3.3 core profile). The goal is to demonstrate practical graphics programming concepts including rendering geometry, transformation matrices, camera systems, texturing, and model loading.

##  Projects Overview

### Tut01 – Hello OpenGL
- Create window with GLFW
- Initialize OpenGL via GLEW
- Draw a triangle using vertex and fragment shaders
- Upload geometry using Vertex Buffer Objects (VBOs)
- Use ‘glVertexAttribPointer’ to link data to shader attributes

Focus: Shader setup, buffer management, rendering pipeline

---

### Tut02 – Transformations
- Apply Model, View, Projection matrices using GLM
- Animate rotation over time
- Toggle between orthographic and perspective views
- Move virtual camera using keyboard input

Focus: World/View/Projection transforms, ‘glm::lookAt’ , matrix uniforms

---

### Tut03 – First/Third Person Camera & Shooting
- Implement first and third person camera controls using spherical coordinates
- Capture mouse movement to rotate camera
- Press keys to move camera (WASD)
- Shoot projectiles that move in 3D space
- Enable depth testing

Focus: Interactive camera, projectiles, view space rendering

---

### Tut04 – Texture Mapping
- Load textures using stb_image.h
- Use UV coordinates in vertex data
- Bind and sample textures in shaders
- Apply different textures to different models
- Render textured and untextured geometry with different shaders

Focus: Texture mapping, UV interpolation, fragment shader sampling

---

### Tut05 – Model Loading & Element Buffer Objects (EBOs)
- Load ‘.obj’ models (e.g., cube, Heracles)
- Visualize models using vertex normals
- Optimize geometry with EBOs to eliminate redundancy
- Use keyboard to switch between models

Focus: Model loading, index buffers, visualizing 3D geometry efficiently

---

## Tools & Libraries

- Language: C++
- Libraries:
  - [GLFW](https://www.glfw.org/) – Window & input handling
  - [GLEW](http://glew.sourceforge.net/) – OpenGL extension loading
  - [GLM](https://glm.g-truc.net/) – OpenGL Mathematics
  - [stb_image.h](https://github.com/nothings/stb) – Image loading

---

## Notes

- Each tutorial is contained in a separate folder (‘Tut01’ , ‘Tut02’ , etc.)
- Projects are structured for easy expansion and testing
- Example code is documented and modular