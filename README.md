# ComicBookBrowser

A simple Android app using Google's Paging library version 3 and view binding. 

---

The app connects with [Marvel's comic book api](https://developer.marvel.com/). Even though the repository name is ComicBookBrowser, it's mostly a browser for Marvel characters. The actual comic book part is opened in a webView inside the app.

# QuickStart

To run this repo, you must create your own api keys from [Marvel](https://developer.marvel.com/), then create a file at the root of the project called `apiKey.properties` that includes two fields: 

    MARVEL_PUBLIC_KEY="XXXXXXXXX"
    MARVEL_SECRET_KEY="XXXXXXXXX"
