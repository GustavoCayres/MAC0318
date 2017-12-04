# 1DLocalization

Comandos interface:

r - reset
m - solicitar movimentação do robô
i - zoom in
o - zoom out

'space bar' - faz uma leitura e atualiza a predição
'up arrow' - movimenta o robô para frente de um tanto fixo, no caso 10 cm
'down arrow' - movimenta o robô para trás de um tanto fixo, no caso 10 cm

## Running without the Robot

```
cd src/
javac MainProgramWithoutRobot.java
java MainProgramWithoutRobot
```

## Cropping images

Crop all images within a directory, using `convert` (requires zsh):

```
./crop_images.sh <directory> <crop_arg>
```

Where crop_arg for each directory is

| directory               | crop_arg      |
| ----------------------- |:-------------:| 
| test_uniform            | 800x400+74+62 |
| test\_correct\_gaussian | 800x400+75+62 |
| test\_wrong\_gaussian   | 800x400+75+62 |
