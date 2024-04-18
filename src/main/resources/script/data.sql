SELECT * FROM users WHERE user_id = 1;

SELECT verify
FROM otps ot inner join public.users u on u.user_id = ot.user_id
WHERE ot.user_id = 27;

SELECT * FROM expenses WHERE expense_id = 1;

SELECT category_id, name, description
FROM categories
WHERE user_id = 1;

SELECT user_id, email, profile_image
FROM users
WHERE user_id = 1