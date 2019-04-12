#Clean Architecture
App made with the Chuck Norris API - https://api.chucknorris.io/.

## Code
Using "Clean Architecture" based on Antonio Leivas and BufferApp implementations.


### Modules
#### domain
In this module, you will find all models (entities)

#### app
In this module, you will find all Android classes and UI.

#### data
In this module you will find all the interfaces/datasources, apis and repos.

#### cache
Where all *offline* data writing occurs, this module depends on *Android* due the use of *Room* for this writing. It also uses a *mapper* to convert the Room entity into application entity.

#### usecases
In this module you will find all *usecases* / *iteractors*.

## Some observations

### Cache Module
Created in order to avoid inserting/injecting *Android* dependencies into the data module. Hence, the *data* module has an *interface* to be implemented on *cache*

### Mapper
Maps up the *Room* *@Entity* at the cache module and passes it into the application *entity*. Hence avoiding *@Entity* to go thru non *Android* modules.


### Koin
Used for dependency injection (easy to use and fulfills all requirements here).


### Continuous Integration 
Using Bitrise.io for this purpose.


## Testing
Tests written for the modules: *app*, *cache*, *data* and *usecases*.


#### app
In this module are tested *ViewModels* and *Activities*. In the *FactsActivityTest* we can verify if the text length at the *RecyclerView* respects the length logic from the source and if the category is being set to *UNCATEGORIZED*. In *SearchActivityTest*, we can verify if there are duplicated values from recent searches at *RecyclerView* 

#### cache
Here we test the write and retrieve actions for the data in *Room*.

#### data
Here we test all repos.


#### usecases
Here we test all *usecases*


## References
* Post: https://antonioleiva.com/clean-architecture-android/
* BufferApp: https://github.com/bufferapp/clean-architecture-components-boilerplate