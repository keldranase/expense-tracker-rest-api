## Expense Tracker REST API

Small REST API, made for the practice sake. Each user, registered in application can have multiple categories of expenses (shopping, entartainment, ...). Each category can have multiple transactions, associated with it.

## Data Storage

Data is stored via PostgreSQL, in three tables: users, categories and transactions. I feel SQL is not the best for this type of data, and NoSql might be better, so I might switch to it later.

## Logic

App has three main entities: user, category and transaction. For each main entity there are Resource, Service and Repository classes. 
Resource class represents and handles all endpoints for its entity. Repository handles operations on database - check, insert, delete, update. Service class is somewhere in between of these two, and handles buisnes like logic. It reduces coupling, so we can modify and extend the software more easily.

## Current  version of API supports
 
* register user
* login user
* update user
* add category for user
* edit category for user
* get all categories for single user  
* delete category, and all transactions for it
* add transaction for category
* edit transaction
* get all transactions for given category
* delete transaction

## Future

There are a lot of flaws in basic structure, but it's fine as playground. This is the list of things, I want to change in similar projects in the future:
* use word-like identifiers in url requests instead of numerical (/api/categories/{categoryId})
* 

