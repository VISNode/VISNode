# Zhang Suen

O algoritmo de Zhang Suen (ZHANG; SUEN, 1984) tem como base a comparação do pixel em processamento com seus 8 vizinhos. A exclusão de pixel por parte do algoritmo somente é realizada mediante a quatro regras. Estas regras têm como objetivo obter a exclusão segura dos pixels, garantindo, desta forma, que áreas interligadas não percam a conectividade e que a eliminação ocorrerá nas bordas do objeto (WOISKI, 2007).

# ![Mask](https://raw.githubusercontent.com/VISNode/VISNode/master/src/main/resources/visnode/pdi/process/image/ZhangSuenProcessMask.png)

O algoritmo é composto por duas iterações que fazem uso das quatro regras descritas na sequência. Na primeira iteração, para as regras C e D, serão utilizada as máscaras descritas pela 1 e Figura 2, respectivamente, na segunda etapa, serão utilizadas as máscaras descritas pela Figura 3 e Figura 4, respectivamente.

Segundo Woiski (2007), para que um pixel seja marcado para exclusão ele deve:
* Possuir sua conectividade maior que 1;
* O objeto deve ser composto de pelo menos dois e no máximo seis pixels pretos;
* Ao menos um dos pixels da primeira máscara deve ser branco;
* Ao menos um dos pixels da segunda máscara deve ser branco;


Figura 1
# ![Mask](https://raw.githubusercontent.com/VISNode/VISNode/master/src/main/resources/visnode/pdi/process/image/ZhangSuenProcessMask1.png)

Figura 2
# ![Mask](https://raw.githubusercontent.com/VISNode/VISNode/master/src/main/resources/visnode/pdi/process/image/ZhangSuenProcessMask2.png)

Figura 3
# ![Mask](https://raw.githubusercontent.com/VISNode/VISNode/master/src/main/resources/visnode/pdi/process/image/ZhangSuenProcessMask3.png)

Figura 4
# ![Mask](https://raw.githubusercontent.com/VISNode/VISNode/master/src/main/resources/visnode/pdi/process/image/ZhangSuenProcessMask4.png)

#### Parâmetros
* __image:__ Imagem

#### Saída
* __image:__ Imagem
