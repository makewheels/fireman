#保存每个用户的提交统计到新表
DROP TABLE IF EXISTS countSubmitLog;
CREATE TABLE countSubmitLog AS
SELECT s.userId,
COUNT(s.result) total,
COUNT(CASE WHEN s.result='true' THEN 1 END) correct,
COUNT(CASE WHEN s.result='false' THEN 1 END) wrong
FROM submitlog s
WHERE s.userId IS NOT NULL
GROUP BY s.userId;

#二次查询，计算正确率
SELECT c.*,u.`username`,correct/total*100 正确率
FROM countSubmitLog c
JOIN usertable u ON c.`userId`=u.`userId`
WHERE c.`total`>=100
ORDER BY correct/total DESC;

DROP TABLE IF EXISTS countSubmitLog;