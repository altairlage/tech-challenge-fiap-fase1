INSERT INTO gastrohub.roles (name) VALUES 
	 ('ROLE_ADMINISTRATOR'),
	 ('ROLE_CUSTOMER');

INSERT INTO gastrohub.users (address,created_at,email,last_updated_at,name,password) VALUES
	 ('Rua das onças, 100','2024-10-25 13:13:13','admin@gastrohub.com','2024-10-25 13:13:13','admin','$2a$10$gOCIURpxosiTIs8CGOm2mOYT4db7Z.2QiE9HZ3upjXkk7JQYm2Q02'), -- swordfish
	 ('Rua das couves, 90','2024-10-25 13:13:13','jose_fulano@gmail.com','2024-10-25 13:13:13','José Fulano','$10$MR6VmM3T1D.uCkVJlGjovu6G5I7PV8k/CNxvSKxtV3sIRtHciQ3AK'); -- s3nh4

INSERT INTO gastrohub.users_roles (user_id,role_id) VALUES
	 (1,1),
	 (2,2);
