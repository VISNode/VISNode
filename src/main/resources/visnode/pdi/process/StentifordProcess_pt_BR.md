# Stentiford

O algoritmo de Stentiford adota uma abordagem baseada na utilização de máscaras para o afinamento de objetos. São utilizadas quatro máscaras que são aplicadas de forma sucessiva e ordenada (WOISKI, 2007).

# ![Mask](https://raw.githubusercontent.com/VISNode/VISNode/master/src/main/resources/visnode/pdi/process/image/StentifordProcessMask.png)

Na Figura a cima são demonstradas as máscaras utilizadas pelo algoritmo, os círculos brancos representam pixels de valor zero, os círculos pretos representam os pixels de valor 1 e os X representam os pixels com valores irrelevantes.

Segundo Woiski (2007), o algoritmo de Stentiford é composto por seis passos:

* Percorrer a imagem até encontrar um pixel que se encaixe na máscara M1;
* Marca o ponto como apagado caso o pixel não seja um ponto final e sua conectividade seja igual a 1;
* Repetir os passos 1 e 2 para todos os pixels da imagem que se encaixem na máscara M1;
* Repetir os passos 1, 2 e 3 para as máscaras M2, M3 e M4;
* Apagar todos os pixels que estejam marcados para serem apagado;∙ Se algum ponto foi apagado no passo 5, os passos a partir do passo 1 devem ser repetidos.

#### Parâmetros
* __image:__ Imagem

#### Saída
* __image:__ Imagem
