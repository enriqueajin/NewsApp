# News App

![Now in Android](docs/news-app.png "Now in Android")

The News App is a sample Android Application to browse, read, 
share and save articles using
[News API](https://newsapi.org/). It follows the principles
of Clean Architecture, utilizes the MVVM (Model-View-ViewModel) 
architectural pattern, and is built using Jetpack Compose. 

# Demo

Watch a video demo on Youtube
[here](https://youtu.be/zH4a3_71lmQ).

# Features

* Browse news through different categories
* View article details:
  * Title
  * Author
  * Published date
  * Picture
  * Content
* Share article with third-party apps.
* Add article to favorites.
* Delete article from favorites.
* Search articles by typing a keyword

# Technologies and Libraries Used

* **Kotlin:** Programming language.
* **Jetpack Compose:** Modern UI toolkit for building native Android UI.
* **Kotlin Coroutines:** For asynchronous operations.
* **Room:** For local data storage.
* **Paging 3:** Allow pagination to efficiently load and display large datasets.
* **Dagger Hilt:** For dependency injection.
* **Retrofit:** For HTTP network requests.
* **Coil:** For image loading coming from API.
* **Navigation:** Allow navigation between screens with arguments.
* **Kotlin Serialization:** JSON serialization/deserialization for arguments when navigating.
* **Hilt Navigation:** Allow ViewModel injection when navigating.

# Architecture

The app follows the principles of Clean Architecture, which 
promotes separation of concerns and modular development. The 
architecture consists of the following layers:

* Presentation: Contains the Jetpack Compose UI components, ViewModels, and UI-related logic.
* Domain: Contains the business logic and defines the use cases of the application.
* Data: Handles data operations, including fetching data from the network and accessing the local database using Room.
* Dependency Injection: Uses Dagger Hilt for dependency injection, enabling modular and testable code.

```markdown
.
├── app
│   ├── manifests
│   ├── kotlin+java
│   │   ├── newsapp
│   │   │   ├── data
│   │   │   │   ├── local
│   │   │   │   ├── network
│   │   │   │   └── repository
│   │   │   ├── di
│   │   │   │   ├── DatabaseModule
│   │   │   │   └── NetworkModule
│   │   │   ├── domain
│   │   │   │   ├── model
│   │   │   │   ├── repository
│   │   │   │   └── use_case
│   │   │   ├── presentation
│   │   │   │   ├── article_detail
│   │   │   │   ├── bottom_bar
│   │   │   │   ├── favorite
│   │   │   │   ├── home
│   │   │   │   ├── keyword_news
│   │   │   │   ├── nav_graph
│   │   │   │   ├── search_news
│   │   │   │   ├── ui
│   │   │   │   │   └── theme
│   │   │   │   ├── MainActivity
│   │   │   │   └── PagingStateHandler
│   │   │   ├── util
│   │   │   │   ├── Constants
│   │   │   │   ├── DateUtils
│   │   │   │   ├── DummyDataProvider
│   │   │   │   ├── KeywordProvider
│   │   │   │   └── TestTags
│   │   │   └── NewsApplication
│   │   ├── newsapp (test)
│   │   └── newsapp (androidTest)
```

# Installation

Use the following steps to run the app.

1. Clone the repository through either `HTTPS` or `SSH`.  

`HTTPS:`
```bash
git clone https://github.com/enriqueajin/NewsApp.git
```

`SSH:`
```bash
git clone git@github.com:enriqueajin/NewsApp.git
```
2.  Create an account and get your API key on
[News API](https://newsapi.org/)


3. Add your `NEWS_API_KEY` within your `gradle.properties` file.

```bash
NEWS_API_KEY=[your_api_key]
```
4. Run the app.

# Testing

* **UI testing**: In progress 🚧
* **Unit Testing**: TODO