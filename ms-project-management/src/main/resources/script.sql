SELECT
	project.name,
	project.description,
	project.create_at,
	project_member.user_nick,
	project_member.role
FROM
	project
JOIN
	project_member
ON
	project.id = project_member.project_id
WHERE
	project_member.user_nick = 'ahi-tech86';