# Property Recommendation System (Console, Java)

Simple Java console application that recommends rental properties based on user preferences (city, neighborhood, type, bedrooms, bathrooms, pet-friendly, furnished, budget) with multiple sorting options and a best-match scorer.

## Requirements

- Java 17+ (or Java 11+, no external dependencies)

## Project Structure

```
src/
  Main.java
  data/PropertyRepository.java
  model/Property.java
  model/UserPreference.java
  recommendation/PropertyRecommender.java
  util/InputUtils.java
```

## Run

From the project root:

```
javac -d out $(find src -name "*.java")
java -cp out Main
```

If `find` is not available (e.g., on Windows PowerShell), compile like:

```
javac -d out src/Main.java src/data/PropertyRepository.java src/model/Property.java src/model/UserPreference.java src/recommendation/PropertyRecommender.java src/util/InputUtils.java
java -cp out Main
```

## Sample Cities

- Metropolis (Downtown, Parkside, Warehouse, Suburbia)
- Gotham (Old Town, Midtown, Industrial)
- Star City (Marina, Greenview, Central)

## Notes

- Data is seeded in-memory in `PropertyRepository`.
- Best Match uses a weighted score combining price fitness, size, rating, and soft matches for type/neighborhood.



