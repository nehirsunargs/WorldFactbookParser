## Overview

The World Factbook Scraper is a Java application that extracts information from the CIA World Factbook website. It provides various functionalities such as retrieving country information, analyzing geographical data, and answering specific questions related to continents, countries, and oceans.

## Features

- Retrieve country information including names, URLs, continents, and plug types.
- Extract geographic data such as coastline length and lowest points of oceans.
- Analyze border countries for each country.
- Search for specific words in flag descriptions.
- Calculate sex ratio disproportionality for bordering countries.
- Find the volume percentage of a given ocean.

## Installation

After cloning the repository, import the project into your Java IDE (preferably vscode). Make sure to install the dependencies (jsoup). Run App.java which will generate a prompt to ask one of the 8 questions.

## Examples

- To find the lowest point in a given ocean:
  ```
  Enter the number of the question you want to ask: 1
  Enter the name of the ocean: Pacific Ocean
  ```

- To list all countries with flags containing a specific word:
  ```
  Enter the number of the question you want to ask: 2
  Enter the word you want to search in flag descriptions: sky
  ```

## Dependencies

- Jsoup: [https://jsoup.org/](https://jsoup.org/)

## Contributors

- [Nehir Sunar](https://github.com/nehirsunargs)
