# Logs Parser
This little application parses log files (with events saved as JSON objects) and saves a summary in HSQLDB database.

## How To Build
`gradle build` will be enough

## How To Run Tests?
`gradle clean test` will be also fine

## How To Run The App?
Have a look here `/build/libs/log-parser-1.0.SNAPSHOT.jar`
Run it with `java -jar log-parser-1.0.SNAPSHOT.jar pathToFileWithLogs`

Remember to provide an argument with path to your log file.

