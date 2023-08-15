insert into company(company_id, name) values (NEXTVAL(company_seq), "Tokyo Metro");
insert into company(company_id, name) values (NEXTVAL(company_seq), "Toei");

insert into line(line_id, company_id, name_kr, name_en, name_jp, number) values (NEXTVAL(line_seq), (select company_id from company where name="Tokyo Metro"), "긴자선", "Ginza Line", "銀座線", "G");
insert into line(line_id, company_id, name_kr, name_en, name_jp, number) values (NEXTVAL(line_seq), (select company_id from company where name="Tokyo Metro"), "마루노우치선", "Marunouchi Line", "丸ノ内線", "M");
insert into line(line_id, company_id, name_kr, name_en, name_jp, number) values (NEXTVAL(line_seq), (select company_id from company where name="Tokyo Metro"), "히비야선", "Hibiya Line", "日比谷線", "H");
insert into line(line_id, company_id, name_kr, name_en, name_jp, number) values (NEXTVAL(line_seq), (select company_id from company where name="Tokyo Metro"), "도자이선", "Tozai Line", "東西線", "T");
insert into line(line_id, company_id, name_kr, name_en, name_jp, number) values (NEXTVAL(line_seq), (select company_id from company where name="Tokyo Metro"), "치요다선", "Chiyoda Line", "千代田線", "C");
insert into line(line_id, company_id, name_kr, name_en, name_jp, number) values (NEXTVAL(line_seq), (select company_id from company where name="Tokyo Metro"), "유라쿠초선", "Yurakucho Line", "有楽町線", "Y");
insert into line(line_id, company_id, name_kr, name_en, name_jp, number) values (NEXTVAL(line_seq), (select company_id from company where name="Tokyo Metro"), "한조몬선", "Hanzomon Line", "半蔵門線", "Z");
insert into line(line_id, company_id, name_kr, name_en, name_jp, number) values (NEXTVAL(line_seq), (select company_id from company where name="Tokyo Metro"), "난보쿠선", "Namboku Line", "南北線", "N");
insert into line(line_id, company_id, name_kr, name_en, name_jp, number) values (NEXTVAL(line_seq), (select company_id from company where name="Tokyo Metro"), "후쿠토신선", "Fukutoshin Line", "副都心線", "F");
insert into line(line_id, company_id, name_kr, name_en, name_jp, number) values (NEXTVAL(line_seq), (select company_id from company where name="Toei"), "아사쿠사선", "Asakusa Line", "浅草線", "A");
insert into line(line_id, company_id, name_kr, name_en, name_jp, number) values (NEXTVAL(line_seq), (select company_id from company where name="Toei"), "미타선", "Mita Line", "三田線", "I");
insert into line(line_id, company_id, name_kr, name_en, name_jp, number) values (NEXTVAL(line_seq), (select company_id from company where name="Toei"), "오에도선", "Oedo Line", "大江戸線", "O");
insert into line(line_id, company_id, name_kr, name_en, name_jp, number) values (NEXTVAL(line_seq), (select company_id from company where name="Toei"), "신주쿠선", "Shinjuku Line", "新宿線", "S");
