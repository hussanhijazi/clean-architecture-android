
# Clean Architecture
App em usando a api do Chuck Norris https://api.chucknorris.io/.

## Código
Estou usando *Clean Architecture* baseada na implementação do Antonio Leiva e da BufferApp.

### Módulos
#### domain
Nesse módulo temos os models(*entities*).

#### app
Nesse módulo temos as classes *Android* e a UI.

#### data
Nesse módulo temos os *interfaces* / *datasources*, api e repositórios.

#### cache
Nesse módulo fazemos as gravação dos dados *offline*, esse módulo depende do *Android*, pois usamos *Room* para gravação dos dados. Usamos um *mapper* para transformar entidade do *Room* para entidade da aplicação.

#### usecases
Nesse módulo temos os *usescases* / *interactors*.

## Alguns pontos

### Módulo Cache
Criamos o módulo *cache* para não colocar depedência do *Android* no módulo data. O módulo *data* tem uma *interface* para ser implementada no *cache*.

### Mapper
Usamos um *mapper* para que o mapemento da *@Entity* do *Room* no módulo cache para a *entity* da aplicação. Assim evitando de passar *@Entity* onde os módulos que não tem *Android*.

### Koin
Usamos o *Koin* para injeção de depêndencias, por achar ele de simples uso e atende a necessidade.

### Integração Contínua
Usamos o Bitrise.io para integração contínua.

## Testes
Temos testes nos módulos: *app*, *cache*, *data*, e *usecases*.

#### app
Nesse módulo testamos os *ViewModels *e as *Activities*. No teste *FactsActivityTest*, verificamos o tamanho do texto no *RecyclerView* esta de acordo com a lógica do tamanho da fonte e se a categoria esta sendo setada como *UNCATEGORIZED*. Em *SearchActivityTest*, verificamos se existem termos duplicados no *RecyclerView* de buscas recentes.

#### cache
Nesse módulo testamos a gravação e carregamento dos dados no *Room*.

#### data
Nesse módulo testamos os repositórios.

#### usecases
Nesse módulo testamos os *usescases*.

## Referências
* Artigo: https://antonioleiva.com/clean-architecture-android/
* BufferApp: https://github.com/bufferapp/clean-architecture-components-boilerplate

