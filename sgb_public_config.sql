INSERT INTO public.config (nome, descricao, valor) VALUES ('MINIMUM_NUMBER_OF_COPIES', '"QUANTIDADE MINIMA DE EXEMPLARES" QUE DEVEM FICAR NA BIBLIOTECA', '2');
INSERT INTO public.config (nome, descricao, valor) VALUES ('ENTRY_TIME_ON_WEEKDAYS', 'HORARIO INICIAL DE FUNCIONAMENTO DOS SERVICOS BIBLIOTECARIO NOS DIAS DA SEMANA', '7');
INSERT INTO public.config (nome, descricao, valor) VALUES ('ENTRY_TIME_ON_SATURDAY', 'HORARIO INICIAL DE FUNCIONAMENTO DOS SERVICOS BIBLIOTECARIO NOE SABADO', '9');
INSERT INTO public.config (nome, descricao, valor) VALUES ('EXIT_TIME_ON_WEEKDAYS', 'HORARIO FINAL DE FUNCIONAMENTO DOS SERVICOS BIBLIOTECARIO NOS DIAS DA SEMANA', '17');
INSERT INTO public.config (nome, descricao, valor) VALUES ('EXIT_TIME_ON_SATURDAY', 'HORARIO FINAL DE FUNCIONAMENTO DOS SERVICOS BIBLIOTECARIO NOE SABADO', '13');
INSERT INTO public.config (nome, descricao, valor) VALUES ('DEADLINE_REQUESTED_BOOKS', 'tempo maximo em minuto, que uma obra requesitada por um utente, permanece disponivel para o levatamento.', '60');
INSERT INTO public.config (nome, descricao, valor) VALUES ('DEADLINE_RESERVED_BOOKS', 'tempo maximo em dias, que uma obra reservada por um utente, permanece disponivel para o levatamento.', '2');
INSERT INTO public.config (nome, descricao, valor) VALUES ('DEADLINE_STUDENT_BORROWED_BOOKS', 'tempo maximo em dias, que uma obra emprestada permanece na posse de um estudante', '3');
INSERT INTO public.config (nome, descricao, valor) VALUES ('DEADLINE_TEACHER_BORROWED_BOOKS', 'tempo maximo em dias, que uma obra emprestada permanece na posse de um docente', '7');