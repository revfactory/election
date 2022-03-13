# election
## Skill SEt
- Spring Boot


## sql
```sql
SELECT T2.name, SUM(T1.vote_count), SUM(invalidity_count), '', SUM(total_count), SUM(candidate1), '', SUM(candidate2), '', SUM(candidate1)-SUM(candidate2)
FROM report T1 LEFT OUTER JOIN town T2 ON T1.town_id = T2.id
WHERE T1.city_id = 15 and (type = 'VOTE')
GROUP BY T1.town_id;
```
