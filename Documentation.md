#  Это набор компонентов для Android-приложения, разработанный в рамках ВКР

---

# Документация

# Для использования необходимо:
- Иметь установленную Android Studio
- Эмулятор на API >= 33
- Иметь опыт разработки под Android, использования Jetpack Compose, Retrofit, Hilt или иметь желание учиться на-ходу

## Установка
- Необходимо зарегестрироваться на Unsplash API, зарегистрировать ваше приложение и скопировать его access-key
- Добавить в корень проекта файл gradle.properties с кодом:
```
android.useAndroidX=true
kotlin.code.style=official
android.nonTransitiveRClass=true
API_KEY="ваш ключ доступа Unsplash"
```

# Разработанные компоненты

## Сборка
В директорию gradle был добавлен libs.versions.toml, в котором находится список всех библиотек и плагинов с их версиями, которые можно использовать в поле dependencies в файлах gradle.kts

Корневой built.gradle.kts имеет вид:
```
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.android.library) apply false
    alias(libs.plugins.hilt) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.kapt) apply false
    alias(libs.plugins.kotlin.parcelize) apply false
    alias(libs.plugins.kotlin.serialization) apply false
    alias(libs.plugins.ksp) apply false
}

tasks.register("clean", Delete::class){
    delete(rootProject.buildDir)
}
```
Где плагины используют .toml файл для подключения конкретного плагина с версией. Так же объявлена задача для удаления build файлов.

В файле setting.gradle.kts
```
rootProject.name = "DiplomaAppComponents"
include(":app")
include(":core:ui")
include(":core:network")
include(":core:base")
include(":features:imageGallery")
include(":features:anotherTestFeature")

pluginManagement {
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}
```
Объявлено имя проекта, все модули и репозитории для загрузки плагинов и библиотек.

# Сетевой компонент

## Класс NetworkCall

Класс `NetworkCall` представляет собой реализацию интерфейса `Call<NetworkResponse<S, E>>` и предназначен для обработки сетевых вызовов. Внутри класса используется делегирование к другому объекту `delegate` типа `Call<S>`, который выполняет фактический сетевой вызов, и объекту `errorConverter` типа `Converter<ResponseBody, E>`, который конвертирует ошибку сетевого вызова в объект типа `E`.

### Метод `enqueue(callback: Callback<NetworkResponse<S, E>>)`
Метод `enqueue` выполняет асинхронный сетевой вызов и передает результаты обратно через объект типа `Callback<NetworkResponse<S, E>>`. Внутри метода используется метод `enqueue` делегата `delegate` для выполнения фактического сетевого вызова.

#### Параметры
- `callback`: Объект типа `Callback<NetworkResponse<S, E>>`, который используется для передачи результатов сетевого вызова.

#### Реализация
В методе `enqueue` определен анонимный объект `Callback<S>`, который служит для обработки результатов фактического сетевого вызова. Внутри этого объекта определены два метода: `onResponse` и `onFailure`, которые вызываются в зависимости от результата сетевого вызова.

- Метод `onResponse` вызывается, когда сетевой вызов выполнен успешно и получен ответ от сервера. Внутри этого метода выполняется проверка на успешность ответа и создание объекта `NetworkResponse` в соответствии с полученными данными. Затем результат передается через объект `callback`.
- Метод `onFailure` вызывается, когда сетевой вызов не выполнен или произошла ошибка. Внутри этого метода определяется тип ошибки и создается соответствующий объект `NetworkResponse`. Затем результат передается через объект `callback`.

### Другие методы

- `clone()`: Метод `clone()` создает и возвращает копию объекта `NetworkCall` с тем же `delegate` и `errorConverter`.
- `execute()`: Метод `execute()` вызывает исключение `UnsupportedOperationException`, поскольку класс `NetworkCall` не поддерживает синхронное выполнение сетевых вызовов.
- `isExecuted()`: Метод `isExecuted()` возвращает `true`, если сетевой вызов уже был выполнен, иначе возвращает `false`.
- `cancel()`: Метод `cancel()` отменяет выполнение сетевого вызова.
- `isCanceled()`: Метод `isCanceled()` возвращает `true`, если сетевой вызов был отменен, иначе возвращает `false`.
- `request()`: Метод `request()` возвращает объект `Request`, который содержит информаци

## Класс NetworkAdapterFactory

Класс `NetworkAdapterFactory` является реализацией абстрактного класса `CallAdapter.Factory` и предназначен для создания адаптеров `CallAdapter` для Retrofit.

### Метод `get(returnType: Type, annotations: Array<Annotation>, retrofit: Retrofit)`

Метод `get` переопределяет абстрактный метод класса `CallAdapter.Factory` и используется для получения адаптера `CallAdapter` на основе типа возвращаемого значения, аннотаций и экземпляра Retrofit.

#### Параметры
- `returnType`: Тип возвращаемого значения метода.
- `annotations`: Массив аннотаций, примененных к методу.
- `retrofit`: Экземпляр Retrofit.

#### Реализация
В методе `get` выполняется следующая логика:
- Проверяется, является ли тип возвращаемого значения `Call`. Если это не так, возвращается `null`.
- Проверяется, является ли тип возвращаемого значения параметризованным типом (`ParameterizedType`).
- Получается тип ответа изнутри типа `Call`.
- Проверяется, является ли тип ответа `NetworkResponse`. Если это не так, возвращается `null`.
- Получаются типы успешного и ошибочного тел ответа.
- Получается конвертер для ошибочного тела ответа.
- Возвращается экземпляр `ResponseAdapter`, который является адаптером `CallAdapter` и используется для обработки ответов.

##NetworkResponse
Все сетевые компоненты оперируют этим классом, который является плоским герметизированным классом, предназначенным для представления различных типов ответов от сервера при выполнении сетевых вызовов.

### Варианты классов

#### `Success<T : Any>`

- `body: T`: Поле, содержащее успешное тело ответа типа `T`.
- `NetworkResponse<T, Nothing>`: Указывает, что это успешный ответ без ошибок.

#### `ApiError<U : Any>`

- `body: U`: Поле, содержащее тело ответа типа `U`, представляющее ошибку API.
- `code: Int`: Поле, содержащее код ошибки.
- `NetworkResponse<Nothing, U>`: Указывает, что произошла ошибка API.

#### `NetworkConnectionError`

- `error: IOException`: Поле, содержащее объект `IOException`, представляющий ошибку сетевого подключения.
- `NetworkResponse<Nothing, Nothing>`: Указывает, что произошла ошибка сетевого подключения.

#### `UnknownError`

- `error: Throwable?`: Поле, содержащее объект `Throwable`, представляющий неизвестную ошибку.
- `NetworkResponse<Nothing, Nothing>`: Указывает, что произошла неизвестная ошибка.

## Использование компонента
Необходимо объявить метод для обращения к серверу, а ответ обернуть в класс Network response, пример:

```
@Headers("Authorization: Client-ID ${BuildConfig.API_KEY}")
    @GET("/search/photos")
    suspend fun searchImages(
        @Query("query")query: String,
        @Query("page")page: Int,
        @Query("per_page")perPage: Int,
        @Query("order_by")orderBy: String
    ): NetworkResponse<SearchResponse, SomeError>
```
Вызывать его можно как обычный метод `val response = unsplashApi.searchImages(...)`

Обработка ответа:
```
when (response){
        is NetworkResponse.Success -> {
            //обработка успешного ответа
        }
        is NetworkResponse.ApiError -> {
            //обработка ошибок с сервера
        }
        is NetworkResponse.NetworkConnectionError -> {
            //...
        }
        is NetworkResponse.UnknownError -> {
            //...
        }
    }
```         

# Компонент навигации

## NavigationCommand
Это интерфейс обозначающий команду навигации
```
interface NavigationCommand {
    val destination: String
    val configuration: NavOptions
        get() = NavOptions.Builder().build()
}
```
Где `destination` - это строковая константа, обозначающая экран. А `configuration` - это дополнительные опции для навигации (для более детальной информации об опциях см. оф. документацию `NavOptions`)

## NavigationDestination
Это запечатанный класс, представляющий собой конкретное назначение для навигации.
```
 sealed class NavigationDestination(
    val route: String
) {
    object ImageGallery : NavigationDestination("imageGallery")
    
    ...
}
```

## NavigationFactory
Это интерфейс для создания графа навигации.
```
interface NavigationFactory {
    fun create(builder: NavGraphBuilder, navigationManager: NavigationManager)
}
```
Пример использования:
```
class ImageGalleryNavigationFactory @Inject constructor() : NavigationFactory {

    override fun create(builder: NavGraphBuilder, navigationManager: NavigationManager) {
        builder.composable(ImageGallery.route) {
            ImageGalleryRoute(navigationManager = navigationManager)
        }
    }
}
```
Здесь `ImageGalleryRoute` - это `@Composable` функция, которая обозначает экран.

В DI модуле необходимо объявить имплементацию фабрики
```
@Singleton
@Binds
@IntoSet
fun bindImageGalleryNavigationFactory(factory: ImageGalleryNavigationFactory): NavigationFactory
```
Такой подход к фабрикам навигации позволяет легче настраивать граф навигации, так граф создается в модуле `core`, но при этом `core` не подключает feature-модули с экранами для навигации, так как их предоставляет `Hilt`. 

## Класс NavigationManager

`NavigationManager` является классом, представляющим менеджер навигации, который обрабатывает переходы и взаимодействие с навигационным компонентом.

### Аннотация

- `@Singleton`: Аннотация, указывающая, что экземпляр класса `NavigationManager` является синглтоном и будет использоваться в единственном экземпляре.

### Конструктор

- `@MainImmediateScope private val externalMainImmediateScope: CoroutineScope`: Приватное поле, представляющее внешнюю область CoroutineScope, связанную с главным потоком выполнения.

### Поля

- `navigationChannel`: Канал типа `Channel<NavigationCommand>`, используемый для отправки команд навигации.
- `navigationEvent`: Поток типа `Flow<NavigationCommand>`, который получает события навигации из `navigationChannel`.

### Методы

#### `navigate(command: NavigationCommand)`

- Принимает параметр `command` типа `NavigationCommand`, представляющий команду навигации.
- Запускает новый корутин внутри `externalMainImmediateScope`.
- Отправляет `command` в `navigationChannel` для выполнения навигации.

#### `navigateBack()`

- Запускает новый корутин внутри `externalMainImmediateScope`.
- Отправляет объект типа `NavigationCommand` в `navigationChannel` с заданным значением `destination`, указывающим на возврат назад в навигации.

Так менеджер навигации подключен в `MainActivity`, где он получает команды навигации из flow `navigationEvent` и сообщает `navController` о необходимости снавигироваться по конкретному назначению.
```
navigationManager
    .navigationEvent
    .collectWithLifecycle(key = navController) {
        when (it.destination) {
            NavigationDestination.Back.route -> navController.navigateUp()
            else -> navController.navigate(it.destination, it.configuration)
        }
    }
```
Это позволяет в местах, где необходимо выполнять навигацию, подключать не navController, а `NavigationManager` который значительно более легковесный и безопасный для подключения.

## NavHost
`NavHost` создается с помощью функции
```
@Composable
fun NavigationHost(
    navController: NavHostController,
    factories: Set<NavigationFactory>,
    modifier: Modifier = Modifier,
    navigationManager: NavigationManager
) {
    NavHost(
        navController = navController,
        startDestination = NavigationDestination.ImageGallery.route,
        modifier = modifier
    ){
        factories.forEach {
            it.create(this, navigationManager)
        }
    }
}
```
Где просто вызываются методы `create` у каждой фабрики, которые были предоставлены с помощью `Hilt`.

# Пагинация
## Класс UnsplashRemoteMediator

`UnsplashRemoteMediator` является классом, реализующим интерфейс `RemoteMediator` для обработки удаленных данных и их сохранения в локальной базе данных.

### Конструктор

- `private val unsplashApi: UnsplashApi`: Приватное поле, представляющее экземпляр `UnsplashApi`, используемый для получения изображений из удаленного источника.
- `private val unsplashDatabase: UnsplashDatabase`: Приватное поле, представляющее экземпляр `UnsplashDatabase`, используемый для доступа к локальной базе данных.
- `private val sortOrder: String`: Приватное поле, представляющее порядок сортировки изображений.

### Поля

- `unsplashImageDao`: Поле типа `UnsplashImageDao`, представляющее доступ к методам работы с изображениями в локальной базе данных.
- `unsplashRemoteKeysDao`: Поле типа `UnsplashRemoteKeysDao`, представляющее доступ к методам работы с ключами удаленных данных в локальной базе данных.

### Методы

#### `load(loadType: LoadType, state: PagingState<Int, UnsplashImageResponse>): MediatorResult`

- Принимает параметры `loadType` типа `LoadType` (тип загрузки) и `state` типа `PagingState<Int, UnsplashImageResponse>` (состояние пагинации).
- Выполняет загрузку данных из удаленного источника в соответствии с типом загрузки.
- Обрабатывает полученный ответ и сохраняет данные в локальной базе данных.
- Возвращает `MediatorResult` (результат медиатора) для указания состояния операции загрузки.

#### `handleResponse(response: NetworkResponse<List<UnsplashImageResponse>, SomeError>, currentPage: Int, loadType: LoadType): MediatorResult`

- Принимает параметры `response` типа `NetworkResponse<List<UnsplashImageResponse>, SomeError>` (ответ сетевого запроса), `currentPage` типа `Int` (текущая страница), `loadType` типа `LoadType` (тип загрузки).
- Обрабатывает различные типы ответов и выполняет соответствующие действия, такие как сохранение данных или генерацию ошибки.
- Возвращает `MediatorResult` (результат медиатора) для указания состояния операции загрузки.

#### `mapRemoteKeys(loadType: LoadType, state: PagingState<Int, UnsplashImageResponse>): UnsplashRemoteKeysDb?`

- Принимает параметры `loadType` типа `LoadType` (тип загрузки) и `state` типа `PagingState<Int, UnsplashImageResponse>` (состояние пагинации).
- Возвращает объект типа `UnsplashRemoteKeysDb`, представляющий ключи удаленных данных для указанного типа загрузки.

#### `parseSuccessResponseInDb(currentPage: Int, endOfPaginationReached: Boolean, loadType: LoadType, response: NetworkResponse.Success<List<UnsplashImageResponse>>): Unit`

- Принимает параметры `currentPage` типа `Int` (текущая страница), `endOfPaginationReached` типа `Boolean` (флаг достижения конца пагинации), `loadType` типа `LoadType` (тип загрузки), `response` типа `NetworkResponse.Success<List<UnsplashImageResponse>>` (успешный ответ с данными изображений).
- Обрабатывает успешный ответ, сохраняет данные в локальной базе данных и обновляет ключи удаленных данных.

#### `getRemoteKeyClosestToCurrentPosition(state: PagingState<Int, UnsplashImageResponse>): UnsplashRemoteKeysDb?`

- Принимает параметр `state` типа `PagingState<Int, UnsplashImageResponse>` (состояние пагинации).
- Возвращает объект типа `UnsplashRemoteKeysDb`, представляющий ключи удаленных данных для элемента, ближайшего к текущей позиции.

#### `getRemoteKeyForFirstItem(state: PagingState<Int, UnsplashImageResponse>): UnsplashRemoteKeysDb?`

- Принимает параметр `state` типа `PagingState<Int, UnsplashImageResponse>` (состояние пагинации).
- Возвращает объект типа `UnsplashRemoteKeysDb`, представляющий ключи удаленных данных для первого элемента в списке данных.

#### `getRemoteKeyForLastItem(state: PagingState<Int, UnsplashImageResponse>): UnsplashRemoteKeysDb?`

- Принимает параметр `state` типа `PagingState<Int, UnsplashImageResponse>` (состояние пагинации).
- Возвращает объект типа `UnsplashRemoteKeysDb`, представляющий ключи удаленных данных для последнего элемента в списке данных.


