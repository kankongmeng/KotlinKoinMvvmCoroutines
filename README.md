# KotlinKoinMvvmCoroutines

Example of android application developed with: <br />
**Kotlin**, **Koin (DI)** and **Android Architecture Components (MVVM)**, **Coroutines**.

# Guidelines

* [Android Architecture Components](#android-architecture-components)
* [Koin (DI)](#koin-dependency-injection)
* [Coroutines](#coroutines)
* [Directory Structure](#directory-structure)
* [Networking](#networking)
* [Unit Test](#unit-tests)


## Android Architecture Components

The app is built around the [Android Architecture Components][1].

The single most important document that every developer is encouraged to read
and understand is the [Guide to App Architecture][2]. It is the standard
resource for the recommended way to architect Android apps by Google.

![](https://developer.android.com/topic/libraries/architecture/images/final-architecture.png)

This repo build using all components shown here with the notable exception of
Room. Broadly speaking, Room provides an abstraction layer to store data
locally in a database. The data provided can be used as the single source of
truth and for offline access. A mechanism like that can easily be added later
on at the repository layer with minimal to no changes to the "user" code.

### Views

Views (Activities/Fragments) subscribe to changes in the ViewModel using
LiveData and update the UI accordingly.

The `BaseFragment` class has a generic parameter that specifies the type of
ViewModel that should be used for the fragment.

### ViewModel

Each Activity has a one-to-one mapping to a ViewModel. Normally, all fragments
managed by the activity will use the same ViewModel to share data. The
ViewModel is lifecycle aware, which means no additional code is required to
ensure data persistence during configuration changes (e.g. screen rotations),
or when fragments are recreated/reattached.

ViewModels are not manually instantiated. Instead, the helper class
`ViewModelProviders` from the Architecture Components is used to locate and
retain instances.

Each ViewModel can have multiple Repositories attached to it, depending on the
kind of information it requires to prepare the data for the view layer.

#### LiveData Events

Normal `LiveData` objects are treated as "continuous" streams of updates to the
views layer. This does not work well with "one-time" events such as: Displaying
an alert dialog, toast message, or navigating somewhere else. For these cases
use the lightweight `Event` class, which keeps track of whether or not it has
been handled.

### Repository

The Repository layer is the data access layer of the app. It's the only layer
that interacts with a remote API, reads data from shared preferences, or from a
database.


## Koin Dependency Injection

[Koin][3] is a lightweight dependency injection framework, that uses Kotlin’s DSL
to lazily resolve your dependency graph at runtime. Koin is Written in pure Kotlin
using functional resolution only: no proxy, no code generation, no reflection!


## Coroutines

Coroutines, are light-weight thread, can use to write asynchronous code that is
perfectly readable and maintainable. Kotlin provides the building block of asynchronous
programming with a single language construct.


## Directory Structure

The top-level directories consist of:

```
/
├── activity
├── adapter
├── api
├── core
├── module
├── fragment
├── model
├── repository
├── viewmodel
```

Directory/Package | Contains
-- | --
`activity` | Activities in the app
`adapter` | Adapters in the app
`api` | Network related classes (Retrofit/OkHttp)
`core` | Core classes used by multiple layers
`module` | Classes related to dependency injection (Koin)
`fragment` | Fragments in the app
`model` | POJO classes for network requests/responses
`repository` | The Repository layer
`viewmodel` | The ViewModel layer


## Networking

The networking in this app is handled by [Retrofit][4], which is backed by
[OkHttp][5].

Retrofit is a network abstraction layer that allows us to manage network
requests without calling OkHttp directly.

[Gson][6] is used to automatically serialize/deserialize data sent through the
networking layer.


## Unit tests

With the architecture described above it is easy to test the ViewModel and
Repository layer, as only the immediate layer below has to be mocked.

Class under test | Layer that needs to be mocked
-- | --
`ViewModel` | `Repository`
`Repository` | API layer and data sources


#### MockK

[MockK][7] is a mocking library for Kotlin that used to mock objects and prepare
them to have predefined behavior and verify the interaction with them.

[1]: https://developer.android.com/topic/libraries/architecture/index.html
[2]: https://developer.android.com/topic/libraries/architecture/guide.html
[3]: https://insert-koin.io/
[4]: http://square.github.io/retrofit/
[5]: http://square.github.io/okhttp/
[6]: https://github.com/google/gson
[7]: https://mockk.io/
