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
            
            
 
