# TraineePokemonApp
Esse app é um gerenciador dos pokemons favoritos do usuário, esse pode cadastrar o seu time favorito para guardar para si e poder utilizar esse time dentro do jogo, além de funcionar como uma pokedex de status dos pokemons e podendo tambem simular a compatibilidade de tipos entre o time escolhido do usuário com o campeão de Kanto(Fire Red).

## Estrutura do Projeto
- Activities -> Pacote que contem as telas desevolvidas
- Helper -> Pacote que contem a classe com funções auxiliares
- Model -> Pacote que contem os modelos para a resposta da requisição da API utilizada, além de alguns modleos utilizados para lógicas internas do app
- API -> Pacote que contém a classe e a interface para a chamada à API
- Adapter -> Pacote que contem os adapters utilizados para renderizar os itens dos RecyclesViews

## API
A api utilizada foi a `https://pokeapi.co/` , ela é uma RESTFUL API que contem chamadas sobre todos os pokemons já feitos, o aplicativo utilizou somente os 151 primeiros pokemons ou só a 1° geração.

## FireBase
O Firebase foi utilizado para montar a arquitetura de login dos usuários e também para o cadastro do time escolhido junto com a deleção
