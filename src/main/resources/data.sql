use helpdelas_db;

INSERT IGNORE INTO tb_roles (role_id, name) VALUES (1, 'ADMIN');
INSERT IGNORE INTO tb_roles (role_id, name) VALUES (2, 'TECH');
INSERT IGNORE INTO tb_roles (role_id, name) VALUES (3, 'USER');

INSERT IGNORE INTO tb_sectors (sector_id, name_department, name_sector) VALUES (1, "TI", "Suporte Técnico");