# kafka-word-count

## Exercise: [Word Count Per Record](https://jaceklaskowski.github.io/kafka-workshop/exercises/kafka-exercise-Word-Count-Per-Record.html)

Write a new `Kafka` application `WordCountPerLineApp` (using Kafka Producer and Consumer APIs) that does the following:

- Consume records from a `topic`, e.g. input Counts words (in the value of a record)
- Produce records with the unique words and their occurrences (_counts_)
- A record `key -> hello hello world` gives a record with the following value `hello -> 2`, `world -> 1` (and the same key as in the input record)

**(EXTRA)** 

- Produces as many records as there are unique words in the input record with their occurrences (_counts_)
- A record `key -> hello hello world` gives two records in the output, i.e. `(hello, 2)` and `(world, 1 (as (key, value))`
