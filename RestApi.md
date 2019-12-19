

## Create transaction
`POST /transactions`

```json
{
	"fromAccountId": "A8998878",
	"toAccountId": "A7687867673",
	"transactionAmount": 90.897
}
```

HTTP status: `201 -> Created`

## Update transaction

* Bodyless PUT

`PUT /transactions/:transaction_id`

or
* With body

`PUT /transactions`

```json
{
	"transaction_id": "TX10005"
}
```

## Filtering

`http://localhost:8888/api/v1/transactions?account_id=A8998878&created_at=20102018T124755&credit=true`

## Sorting

`http://localhost:8888/api/v1/transactions?account_id=A8998878&created_at=20102018T124755&credit=true&sort=created_at:desc`

## Pagination

`http://localhost:8888/api/v1/transactions?account_id=ACC334455&created_at=20102018T12:47:55&sort=created_at:desc&amount={"lt": 100.55, "gt": 30.45}&page={"offset": 1, "limit": 10}`