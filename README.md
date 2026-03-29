### Jogo da cobrinha em JAVA
Lembra do jogo da cobrinha no nokia? Então....
A ideia aqui é simples, implementar a lógica desse game antigo e assim treinar algumas coisas do semestre atual: conceitos de POO e colaboração aqui no Github.

Ok, e quais são os principais objetivos:
- Usar o sistema de Pull Requests 
- Criar código com sobrescrita de comportamento (`@Override`)
- Criar código com polimorfismo (`extends` e algum lugar que usa da superclasse pra referenciar objetos das subclasses (provavelmente um `ArrayList` faz isso))


### Checklist dos requisitos mínimos
- Hierarquia OO
    - 1 superclasse ([Fruit.java](./src/Fruit.java)), com atributos e métodos.
    - 2 subclasses ([Apple](./src/Apple.java)) com atributo e método sobrescrito
    - Algum lugar que referencie a classe pai ([GamePanel.java:16](./src/GamePanel.java))
- Qualidade do código
    - Mensagens claras
    - Encapsulamento (tem em lugares como *[Snake.getHeadXPos](./src/Snake.java)* e [Snake.kill](./src/Snake.java), [GamePanel.addToScore](./src/GamePanel.java))
- Execução
    - *Assim, o jogo roda bonitinho, então podemos considerar esse só "check"?

**O que a gente não faz por uns pontinhos no semestre, não?*

### Algumas regras desse projeto
- O código será escrito em inglês
- As features e bugfixes vêm das issues
- Todo PR vai pra branch `dev`, ok?
- As branches seguem a seguinte forma de escrita: `[PREFIXO]/[PALAVRAS_CHAVE_DA_EVOLUCAO]`.
    - Ex: `feat/fruit-apple`, `fix/gameover-screen`...
- Toda branch vai ter como prefixo: 
    - `feat`: Para novas features
    - `fix`: Para resolução de problemas
    - `refactor`: Para refatorações para melhoria dos códigos
- As mesnagens de commit devem ser escritas em portugues, usando o passado como tempo verbal, para indicar que naquele ponto a nova funcionalidade ou bug foi resolvido. Quer ver alguns exemplos:
    - `Adicionado README ao projeto`
    - `Criado classe 'Snake'`
    - `Resolvido bug que não deixava as frutas renderizarem`

### Integrantes
| | Nome | RA |
| --- | --- | --- |
| <img src="https://avatars.githubusercontent.com/u/130322697?v=4" style="display: inline-block; width: 64px"> | Paulo Vitor Amorim de Oliveira | 42322453
| <img src="https://avatars.githubusercontent.com/u/129918519?v=4" style="display: inline-block; width: 64px"> | Lucas Ferreira Andrade | 4231921505
| <img src="https://avatars.githubusercontent.com/u/35999467?v=4" style="display: inline-block; width: 64px"> | Caio Alves Fernandes *(eu ai ó)* | 4231925609

#### O que eu fiz pra rodar esse trem aqui:
- Instalo o java bonito e adiciono o caminho da pasta do '\\bin' na variável PATH do Windows
- Verifico se os comandos `javac` e `java` rodam no cmd
- Abri o projeto no [VSCodium](https://vscodium.com/), mas qualquer editor de texto serve, tá?
- Faço meu codiguin
- no terminal, digito `compile-and-run.bat` e do nada: BAM! Joguinho tá rodando.