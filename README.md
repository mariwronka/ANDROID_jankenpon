# 👊🏻 ✋🏻✌🏻 Jankenpon - Android Case Study

A versão japonesa do clássico jogo de escolhas entre pedra, papel e tesoura, usado para decisões ou como brincadeira entre duas pessoas. Os jogadores contam em voz alta “Jan-ken-pon!” e mostram simultaneamente um dos três gestos com as mãos. Cada gesto vence um e perde para outro, seguindo a lógica: pedra quebra tesoura, tesoura corta papel, papel embrulha pedra. A proposta é simples: um jogo de "Pedra, Papel e Tesoura" (Jankenpon), mas estruturado com boas práticas, arquitetura moderna, testes e atenção à qualidade de código.

## 🎮 Demonstração
![HomeActivity](https://media0.giphy.com/media/v1.Y2lkPTc5MGI3NjExNmJseWhrZ2hsMXZjeTByYWtuM2l4eWRnbXM0ZjQ5bzEwYjBqdDNvbSZlcD12MV9pbnRlcm5hbF9naWZfYnlfaWQmY3Q9Zw/E3kfOn8K78bftk04Te/giphy.gif)
![Gameplay](https://media1.giphy.com/media/v1.Y2lkPTc5MGI3NjExM3FmazF2Mzd5eTl1bThsMjRjbGkyOW9lNmc4Z3RxcTNxazhvOHNydiZlcD12MV9pbnRlcm5hbF9naWZfYnlfaWQmY3Q9Zw/8ZnKx2C44s2ApxWsSm/giphy.gif) 

*Confira o funcionamento completo do app, incluindo animações, placar e alerta de vitória.*

- Escolha entre Pedra, Papel e Tesoura
- Animação de mãos nos jogadores para tornar o jogo mais fluído
- Persistência de placar com DataStore
- Tela de Ranking com vitórias acumuladas
- Alerta visual com SVG dinâmico
- Arquitetura modular com separação em camadas, futuramento evoluindo para monurarização

## 🛠️ Tecnologias e Padrões

| Categoria              | Ferramentas e Abordagens                                               |
|------------------------|------------------------------------------------------------------------|
| Linguagem              | Kotlin                                                                 |
| Arquitetura            | MVVM + StateFlow + Clean Architecture                                  |
| UI                     | View System + Compose                                                  |
| Injeção de Dependência | Koin                                                                   |
| Persistência           | Jetpack DataStore                                                      |
| Consumo de API         | Retrofit + OkHttp + Moshi                                              |
| Testes                 | JUnit4 + MockK + Robolectric + KoinTestRule                            |
| Qualidade de Código    | Detekt + Spotless (futuramente Kover)                                  |
| CI                     | Gradle Tasks (ideia para o futuro)                                     |       

## 🚀 Como rodar o projeto

1. Clone o repositório:
   ```bash
   git clone https://github.com/mariwronka/ANDROID_jankenpon.git
   cd ANDROID_jankenpon

2. Execute os testes:
   ```bash
   ./gradlew testDebugUnitTest

3. Rode no Android Studio (API mínima: 24)

## 🧪 Testes
O projeto conta com testes unitários para:

- Repositórios (`PlayersRepositoryImpl`)
- ViewModels (`PlayersViewModel`)
- Mapeamentos (`JankenponMapper`)
- Entidades (`JankenponType`, `WinnerType`)
- Cobertura de testes obtida via Android Studio Reports.

## Estrutura
```bash
📦 data
┣ 📂 mapper
┣ 📂 repository
┣ 📂 source
┃ ┣ 📂 local (DataStore)
┃ ┗ 📂 remote (API)
📦 domain
┣ 📂 entity
┣ 📂 repository
📦 ui
┣ 📂 activities
┣ 📂 common
┃ ┣ 📂 base
┃ ┗ 📂 extensions
┣ 📂 components
┗ 📂 viewmodels
```

## 🧠 Decisões Técnicas
- Uso de StateFlow<BaseUiState<T>> para controle de estado robusto e testável
- Separação clara entre camadas (domain, data, ui)
- Testes baseados em BaseViewModelTest para reaproveitamento e clareza
- Custom components com ViewBinding e XML modularizados
  
## 📈 Melhorias Futuras

- Migrar UI para Jetpack Compose de forma gradual (HomeActivityCompose já iniciada)
- Modularizar em :core, :designsystem, :features para escalar o projeto
- Adicionar testes instrumentados para RankingActivity e componentes visuais
- Criar abstração de Result/Error para respostas da API
- Adicionar coverage via Kover e Github Actions
- Internacionalização (strings em inglês)
- Tela de configurações e reset de placar

##  Autoria
- Desenvolvido por Mari Wronka 👩🏻‍🦰
