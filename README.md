## Expense Tracker REST API

Each user, registered in application can have multiple categories of expenses (shopping, entartainment, ...). Each category can have multiple transactions, associated with it.

## Data

All data is stored in PostgreSQL, in three tables: users, categories and transactions. It's possible to painlessly switch database, because it's hidden behind interface.

## Internal Organization

App has three main entities: users, categories and transactions. For each main entity there are Resource, Service and Repository classes. Resource class represents and handles all endpoints for it's entity. Repository handles operations on database - check, insert, delete, update. Service class is somewhere in between of these two, and it servse as additional abstraction level between endpoints and databases. It reduces coupling, so we can modify and extend the software more easily.

## Current  version of API supports
 
* register user
* login user
* update user
* add category for user
* edit category for user
* delete category, and all transactions for it
* add transaction for category
* edit transaction
* delete transaction

