-- Inserindo um atributtes do servidor Dell PowerEdge R250 (pe_r250_15318_bcc_1) na tabela "product_attribute"
INSERT INTO product_attribute (product_id, name, "value")
VALUES (1, 'Modelo', 'PowerEdge R250'),
       (1, 'Tipo', 'Servidor em rack'),
       (1, 'Processador', 'Intel® Pentium G6405T'),
       (1, 'Memória', '1x 8GB, 3200MHz, suporta até 128GB'),
       (1, 'Armazenamento', '1x 2TB HD SATA, suporta até 2 discos de 2TB'),
       (1, 'Sistema Operacional', 'Opcional'),
       (1, 'Assistência', '3 anos de assistência básica'),
       (1, 'Expansão de Memória', 'Suporta até 128 GB de memória DDR4 com DIMMs de até 32 GB e velocidades de até 3200 MT/s'),
       (1, 'Expansão de Armazenamento', 'Suporta até 2 discos rígidos de 2TB cada'),
       (1, 'Expansão PCIe', 'Suporta PCIe Gen 4 para dispositivos de última geração'),
       (1, 'Eficiência Térmica', 'Eficiência térmica aprimorada para atender requisitos térmicos e de energia crescentes');

-- Inserindo atributos do servidor Dell PowerEdge R250 (pe_r250_15318_bcc_2) na tabela "product_attribute"
INSERT INTO product_attribute (product_id, name, "value")
VALUES (2, 'Modelo', 'PowerEdge R250'),
       (2, 'Tipo', 'Servidor em rack'),
       (2, 'Processador', 'Intel® Xeon E-2324G (3,10 GHz)'),
       (2, 'Memória', '16GB (2x 8GB) DDR4 3200MHz'),
       (2, 'Armazenamento', '2x 2TB'),
       (2, 'Sistema Operacional', 'Opcional'),
       (2, 'Assistência', '3 anos de assistência básica'),
       (2, 'Expansão de Memória', 'Suporta até 128 GB de memória DDR4 com DIMMs de até 32 GB e velocidades de até 3200 MT/s'),
       (2, 'Expansão de Armazenamento', 'Suporta até 4 discos rígidos'),
       (2, 'Expansão PCIe', 'Suporta PCIe Gen 4'),
       (2, 'Eficiência Térmica', 'Eficiência térmica aprimorada para atender requisitos térmicos e de energia crescentes');


INSERT INTO product_attribute (product_id, name, "value")
VALUES (3, 'Modelo', 'PowerEdge R450'),
       (3, 'Tipo', 'Servidor em rack'),
       (3, 'Processador', 'Processadores escaláveis Intel® Xeon® de 3ª geração'),
       (3, 'Memória', '16 slots de memória DIMM DDR4, compatíveis com RDIMM de no máximo 1 TB e velocidades de até 2933 MT/s'),
       (3, 'Armazenamento', 'Compartimentos frontais: até 4 SAS/SATA (HDD/SSD) de 3,5 polegadas, máx. de 64 TB; Compartimentos frontais: até 8 unidades de 2,5 polegadas SAS/SATA (disco rígido/SSD), máx. de 61,4 TB'),
       (3, 'Sistema Operacional', 'Suporta Canonical® Ubuntu® Server LTS, Citrix® Hypervisor®, Microsoft® Windows Server® com Hyper-V, Red Hat® Enterprise Linux, Servidor SUSE® Linux Enterprise, VMware® ESXi®'),
       (3, 'Assistência', 'Não especificado'),
       (3, 'Slots de Memória', '16 slots de memória DIMM DDR4, compatíveis com RDIMM de no máximo 1 TB e velocidades de até 2933 MT/s'),
       (3, 'Controladores de Armazenamento', 'Controladores internos (RAID): PERC H345, PERC H355, HBA355i, PERC H745, PERC H755, S150; Boot interno: módulo SD duplo interno ou subsistema de armazenamento com inicialização otimizada (BOSS-S1): HWRAID 2 SSDs M.2 ou USB; PERC externo (RAID): PERC H840; HBA externo (não RAID): HBA355e'),
       (3, 'Fontes de Alimentação', 'Modo misto Platinum de 600 W (100–240 Vca ou 240 Vcc) redundante de troca a quente; Modo misto Platinum de 800 W (100–240 Vca ou 240 Vcc) redundante de troca a quente'),
       (3, 'Expansão PCIe', 'Até 2 slots PCIe Gen4');


INSERT INTO product_attribute (product_id, name, "value")
VALUES (4, 'Modelo', 'PowerEdge R550'),
       (4, 'Tipo', 'Servidor em rack de 2U'),
       (4, 'Processador', 'Processadores escaláveis Intel® Xeon® de 3ª geração'),
       (4, 'Memória', '16 slots de memória DIMM DDR4, compatíveis com RDIMM de no máximo 1 TB e velocidades de até 2933 MT/s'),
       (4, 'Armazenamento', 'Compartimentos frontais: até 4 SAS/SATA (HDD/SSD) de 3,5 polegadas, máx. de 64 TB; Compartimentos frontais: até 8 unidades de 2,5 polegadas SAS/SATA (disco rígido/SSD), máx. de 61,4 TB'),
       (4, 'Sistema Operacional', 'Suporta Canonical® Ubuntu® Server LTS, Citrix® Hypervisor®, Microsoft® Windows Server® com Hyper-V, Red Hat® Enterprise Linux, Servidor SUSE® Linux Enterprise, VMware® ESXi®'),
       (4, 'Assistência', 'Não especificado'),
       (4, 'Slots de Memória', '16 slots de memória DIMM DDR4, compatíveis com RDIMM de no máximo 1 TB e velocidades de até 2933 MT/s'),
       (4, 'Controladores de Armazenamento', 'Controladores internos (RAID): PERC H345, PERC H355, HBA355i, PERC H745, PERC H755, S150; Boot interno: módulo SD duplo interno ou subsistema de armazenamento com inicialização otimizada (BOSS-S1): HWRAID 2 SSDs M.2 ou USB; PERC externo (RAID): PERC H840; HBA externo (não RAID): HBA355e'),
       (4, 'Fontes de Alimentação', 'Modo misto Platinum de 600 W (100–240 Vca ou 240 Vcc) redundante de troca a quente; Modo misto Platinum de 800 W (100–240 Vca ou 240 Vcc) redundante de troca a quente'),
       (4, 'Expansão PCIe', 'Até 2 slots PCIe Gen4');

-- Inserindo atributos do servidor Dell PowerEdge R450 (pe_r450_15127_bcc_2) na tabela "product_attribute"
INSERT INTO product_attribute (product_id, name, "value")
VALUES (5, 'Modelo', 'PowerEdge R450'),
       (5, 'Tipo', 'Servidor em rack de 1U'),
       (5, 'Processador', 'Processadores escaláveis Intel® Xeon® de 3ª geração'),
       (5, 'Memória', '16 slots de memória DIMM DDR4, compatíveis com RDIMM de no máximo 1 TB e velocidades de até 2933 MT/s'),
       (5, 'Armazenamento', 'Compartimentos frontais: até 4 SAS/SATA (HDD/SSD) de 3,5 polegadas, máx. de 64 TB; Compartimentos frontais: até 8 unidades de 2,5 polegadas SAS/SATA (disco rígido/SSD), máx. de 61,4 TB'),
       (5, 'Sistema Operacional', 'Suporta Canonical® Ubuntu® Server LTS, Citrix® Hypervisor®, Microsoft® Windows Server® com Hyper-V, Red Hat® Enterprise Linux, Servidor SUSE® Linux Enterprise, VMware® ESXi®'),
       (5, 'Assistência', 'Não especificado'),
       (5, 'Slots de Memória', '16 slots de memória DIMM DDR4, compatíveis com RDIMM de no máximo 1 TB e velocidades de até 2933 MT/s'),
       (5, 'Controladores de Armazenamento', 'Controladores internos (RAID): PERC H345, PERC H355, HBA355i, PERC H745, PERC H755, S150; Boot interno: módulo SD duplo interno ou subsistema de armazenamento com inicialização otimizada (BOSS-S1): HWRAID 2 SSDs M.2 ou USB; PERC externo (RAID): PERC H840; HBA externo (não RAID): HBA355e'),
       (5, 'Fontes de Alimentação', 'Modo misto Platinum de 600 W (100–240 Vca ou 240 Vcc) redundante de troca a quente; Modo misto Platinum de 800 W (100–240 Vca ou 240 Vcc) redundante de troca a quente'),
       (5, 'Expansão PCIe', 'Até 2 slots PCIe Gen4');

INSERT INTO product_attribute (product_id, name, "value")
VALUES (6, 'Modelo', 'PowerEdge R550'),
       (6, 'Tipo', 'Servidor de 2U'),
       (6, 'Processador', 'Processadores escaláveis Intel® Xeon® de 3ª geração'),
       (6, 'Memória', '16 slots de memória DIMM DDR4, compatíveis com RDIMM de no máximo 1 TB e velocidades de até 2933 MT/s'),
       (6, 'Armazenamento', 'Compartimentos frontais: até 4 SAS/SATA (HDD/SSD) de 3,5 polegadas, máx. de 64 TB; Compartimentos frontais: até 8 unidades de 2,5 polegadas SAS/SATA (disco rígido/SSD), máx. de 61,4 TB'),
       (6, 'Sistema Operacional', 'Suporta Canonical® Ubuntu® Server LTS, Citrix® Hypervisor®, Microsoft® Windows Server® com Hyper-V, Red Hat® Enterprise Linux, Servidor SUSE® Linux Enterprise, VMware® ESXi®'),
       (6, 'Assistência', 'Não especificado'),
       (6, 'Slots de Memória', '16 slots de memória DIMM DDR4, compatíveis com RDIMM de no máximo 1 TB e velocidades de até 2933 MT/s'),
       (6, 'Controladores de Armazenamento', 'Controladores internos (RAID): PERC H345, PERC H355, HBA355i, PERC H745, PERC H755, S150; Boot interno: módulo SD duplo interno ou subsistema de armazenamento com inicialização otimizada (BOSS-S1): HWRAID 2 SSDs M.2 ou USB; PERC externo (RAID): PERC H840; HBA externo (não RAID): HBA355e'),
       (6, 'Fontes de Alimentação', 'Modo misto Platinum de 600 W (100–240 Vca ou 240 Vcc) redundante de troca a quente; Modo misto Platinum de 800 W (100–240 Vca ou 240 Vcc) redundante de troca a quente'),
       (6, 'Expansão PCIe', 'Até 2 slots PCIe Gen4');

INSERT INTO product_attribute (product_id, name, "value")
VALUES (7, 'Modelo', 'PowerEdge R650xs'),
       (7, 'Tipo', 'Servidor Rack 1U'),
       (7, 'Processador', '1x Intel® Xeon® Silver 4309Y'),
       (7, 'Memória', '1x 16GB, 3200MHz'),
       (7, 'Armazenamento', '1x 480GB SSD SATA RI Hot-plug'),
       (7, 'Sistema Operacional', 'Opcional'),
       (7, 'Assistência', '3 anos de assistência básica'),
       (7, 'Slots de Memória', 'Não especificado'),
       (7, 'Controladores de Armazenamento', 'Não especificado'),
       (7, 'Fontes de Alimentação', 'Não especificado'),
       (7, 'Expansão PCIe', 'Não especificado');


INSERT INTO product_attribute (product_id, name, "value")
VALUES (8, 'Modelo', 'PowerEdge R250'),
       (8, 'Tipo', 'Servidor Rack 1U'),
       (8, 'Processador', 'Intel® Xeon® E-2324G'),
       (8, 'Memória', '1x 16GB, 3200MHz'),
       (8, 'Armazenamento', '1x 4TB HD SATA'),
       (8, 'Sistema Operacional', 'Opcional'),
       (8, 'Assistência', '3 anos de assistência básica'),
       (8, 'Slots de Memória', 'Não especificado'),
       (8, 'Controladores de Armazenamento', 'Sem controllador'),
       (8, 'Fontes de Alimentação', 'Não especificado');



INSERT INTO product_attribute (product_id, name, "value")
VALUES (9, 'Modelo', 'PowerEdge R360'),
       (9, 'Tipo', 'Servidor Rack 1U'),
       (9, 'Processador', '1x Intel® Xeon® E-2434'),
       (9, 'Memória', '1x 16GB, 4800MHz'),
       (9, 'Armazenamento', '2x 480GB SSD SATA Hot Plug'),
       (9, 'Sistema Operacional', 'Opcional'),
       (9, 'Assistência', '3 anos de assistência básica');


INSERT INTO product_attribute (product_id, name, "value")
VALUES (10, 'Modelo', 'PowerEdge R450'),
       (10, 'Tipo', 'Servidor Rack 1U'),
       (10, 'Processador', 'Intel® Xeon® Silver 4309Y'),
       (10, 'Memória', '1x 16GB, 3200MHz'),
       (10, 'Armazenamento', '2x 480GB SSD SATA MU Hot-plug'),
       (10, 'Sistema Operacional', 'Opcional'),
       (10, 'Assistência', '3 anos serviço ProSupport, atendimento de especialistas 24/7');


INSERT INTO product_attribute (product_id, name, "value")
VALUES (11, 'Modelo', 'PowerEdge R7525'),
       (11, 'Tipo', 'Servidor Rack 2U'),
       (11, 'Processador', 'AMD EPYC™ 7443'),
       (11, 'Memória', '1x 16GB, 3200MHz'),
       (11, 'Armazenamento', '1x 480GB SSD SATA RI Hot-plug'),
       (11, 'Sistema Operacional', 'Opcional'),
       (11, 'Assistência', '3 anos serviço ProSupport, atendimento de especialistas 24/7');

INSERT INTO product_attribute (product_id, name, "value")
VALUES (12, 'Modelo', 'PowerEdge R360'),
       (12, 'Tipo', 'Servidor Rack 1U'),
       (12, 'Processador', '1x Intel® Xeon® E-2434'),
       (12, 'Memória', '2x 16GB, 4800MHz'),
       (12, 'Armazenamento', '2x 960GB SSD SATA Hot Plug'),
       (12, 'Sistema Operacional', 'Windows Server 2022 Essentials'),
       (12, 'Assistência', '1 ano serviço ProSupport');

INSERT INTO product_attribute (product_id, name, "value")
VALUES (13, 'Modelo', 'PowerEdge R250'),
       (13, 'Tipo', 'Servidor Rack 1U'),
       (13, 'Processador', 'Intel® Xeon® E-2324G'),
       (13, 'Memória', '1x 16GB, 3200MHz'),
       (13, 'Armazenamento', '2x 480GB SSD SATA RI Hot-plug'),
       (13, 'Sistema Operacional', 'Opcional'),
       (13, 'Assistência', ' 3 anos assistência básica');


INSERT INTO product_attribute (product_id, name, "value")
VALUES (14, 'Modelo', 'PowerEdge R660'),
       (14, 'Tipo', 'Servidor Rack 1U'),
       (14, 'Processador', '1x Intel® Xeon® Silver 4410Y'),
       (14, 'Memória', '1x 16GB, 4800MHz'),
       (14, 'Armazenamento', '1x 480GB SSD RI Hot Plug'),
       (14, 'Sistema Operacional', 'opcional'),
       (14, 'Assistência', '3 anos serviço ProSupport, atendimento de especialistas 24/7');

INSERT INTO product_attribute (product_id, name, "value")
VALUES (15, 'Modelo', 'PowerEdge R6625'),
       (15, 'Tipo', 'Servidor Rack 1U'),
       (15, 'Processador', '2x AMD EPYC 9124'),
       (15, 'Memória', '2x 16GB, 4800MHz'),
       (15, 'Armazenamento', '1x 480GB SSD SATA Hot Plug'),
       (15, 'Sistema Operacional', 'opcional'),
       (15, 'Assistência', '3 anos serviço ProSupport, atendimento de especialistas 24/7');


INSERT INTO product_attribute (product_id, name, "value")
VALUES (16, 'Modelo', 'PowerEdge R6625'),
       (16, 'Tipo', 'Servidor Rack 1U'),
       (16, 'Processador', '1x Intel® Xeon® Silver 4410Y'),
       (16, 'Memória', '1x 16GB, 4800MHz'),
       (16, 'Armazenamento', '2x 480GB SSD SATA Hot Plug'),
       (16, 'Sistema Operacional', 'opcional'),
       (16, 'Assistência', '3 anos assistência básica');

INSERT INTO product_attribute (product_id, name, "value")
VALUES (17, 'Modelo', 'PowerEdge R760xd2'),
       (17, 'Tipo', 'Servidor Rack 2U'),
       (17, 'Processador', '1x Intel® Xeon® Silver 4410Y'),
       (17, 'Memória', '1x 16GB, 4800MHz'),
       (17, 'Armazenamento', '2x 2TB HD SATA Hot Plug'),
       (17, 'Sistema Operacional', 'opcional'),
       (17, 'Assistência', '3 anos serviço ProSupport, atendimento de especialistas 24/7');

INSERT INTO product_attribute (product_id, name, "value")
VALUES (18, 'Modelo', 'PowerEdge R6615'),
       (18, 'Tipo', 'Servidor Rack 1U'),
       (18, 'Processador', 'AMD EPYC™ 9124'),
       (18, 'Memória', '2x 16GB, 4800MHz'),
       (18, 'Armazenamento', '2x 480GB SSD SATA Hot Plug'),
       (18, 'Sistema Operacional', 'opcional'),
       (18, 'Assistência', '3 anos assistência básica');

INSERT INTO product_attribute (product_id, name, "value")
VALUES (19, 'Modelo', 'PowerEdge R6615 com Windows Server®'),
       (19, 'Tipo', 'Servidor Rack 1U'),
       (19, 'Processador', 'AMD EPYC™ 9124'),
       (19, 'Memória', '2x 16GB, 4800MHz'),
       (19, 'Armazenamento', '2x 960GB SSD SATA Hot Plug'),
       (19, 'Sistema Operacional', 'Windows Server 2022 Standard'),
       (19, 'Assistência', '3 anos assistência básica');

INSERT INTO product_attribute (product_id, name, "value")
VALUES (20, 'Modelo', 'PowerEdge R7625 com Windows Server®'),
       (20, 'Tipo', 'Servidor Rack 2U'),
       (20, 'Processador', '2x AMD EPYC 9124'),
       (20, 'Memória', '2x 16GB, 4800MHz'),
       (20, 'Armazenamento', '1x 480GB SSD SATA Hot Plug'),
       (20, 'Sistema Operacional', 'opcional'),
       (20, 'Assistência', '3 anos serviço ProSupport, atendimento de especialistas 24/7');

INSERT INTO product_attribute (product_id, name, "value")
VALUES (21, 'Modelo', 'PowerEdge R760xs'),
       (21, 'Tipo', 'Servidor Rack 2U'),
       (21, 'Processador', '1x Intel® Xeon® Silver 4410Y'),
       (21, 'Memória', '1x 16GB, 4800MHz'),
       (21, 'Armazenamento', '1x 8TB HD SATA Hot Plug'),
       (21, 'Sistema Operacional', 'opcional'),
       (21, 'Assistência', '3 anos assistência básica');

INSERT INTO product_attribute (product_id, name, "value")
VALUES (22, 'Modelo', 'PowerEdge R6615'),
       (22, 'Tipo', 'Servidor Rack 1U'),
       (22, 'Processador', 'AMD EPYC™ 9124'),
       (22, 'Memória', '1x 16GB, 4800MHz'),
       (22, 'Armazenamento', '1x 480GB SSD SATA Hot Plug'),
       (22, 'Sistema Operacional', 'opcional'),
       (22, 'Assistência', '3 anos assistência básica');

INSERT INTO product_attribute (product_id, name, "value")
VALUES (23, 'Modelo', 'PowerEdge R6525'),
       (23, 'Tipo', 'Servidor Rack 1U'),
       (23, 'Processador', 'AMD EPYC™ 7343'),
       (23, 'Memória', '1x 16GB, 3200MHz'),
       (23, 'Armazenamento', '1x 480GB SSD SATA RI Hot-plug'),
       (23, 'Sistema Operacional', 'opcional'),
       (23, 'Assistência', '3 anos serviço ProSupport, atendimento de especialistas 24/7');

INSERT INTO product_attribute (product_id, name, "value")
VALUES (24, 'Modelo', 'PowerEdge R650'),
       (24, 'Tipo', 'Servidor Rack 1U'),
       (24, 'Processador', 'Intel® Xeon® Silver 4310'),
       (24, 'Memória', '2x 16GB, 3200MHz'),
       (24, 'Armazenamento', '3x 480GB SSD SATA RI Hot-plug'),
       (24, 'Sistema Operacional', 'opcional'),
       (24, 'Assistência', '3 anos assistência básica');

INSERT INTO product_attribute (product_id, name, "value")
VALUES (25, 'Modelo', 'PowerEdge R660xs'),
       (25, 'Tipo', 'Servidor Rack 1U'),
       (25, 'Processador', '1x Intel® Xeon® Silver 4410Y'),
       (25, 'Memória', '1x 16GB, 4800MHz'),
       (25, 'Armazenamento', '2x 480GB SSD SATA Hot Plug'),
       (25, 'Sistema Operacional', 'opcional'),
       (25, 'Assistência', '3 anos assistência básica');

INSERT INTO product_attribute (product_id, name, "value")
VALUES (26, 'Modelo', 'PowerEdge R360'),
       (26, 'Tipo', 'Servidor Rack 1U'),
       (26, 'Processador', '1x Intel® Pentium G7400T'),
       (26, 'Memória', '1x 16GB, 4800MHz'),
       (26, 'Armazenamento', '1x 2TB HD SATA Hot Plug'),
       (26, 'Sistema Operacional', 'opcional'),
       (26, 'Assistência', '3 anos assistência básica');


INSERT INTO product_attribute (product_id, name, "value")
VALUES (27, 'Modelo', 'PowerEdge R760'),
       (27, 'Tipo', 'Servidor Rack 2U'),
       (27, 'Processador', '1x Intel® Xeon® Silver 4410Y'),
       (27, 'Memória', '1x 16GB, 4800MHz'),
       (27, 'Armazenamento', '1x 8TB HD SATA Hot Plug'),
       (27, 'Sistema Operacional', 'opcional'),
       (27, 'Assistência', '3 anos serviço ProSupport, atendimento de especialistas 24/7');

INSERT INTO product_attribute (product_id, name, "value")
VALUES (28, 'Modelo', 'PowerEdge R760xs'),
       (28, 'Tipo', 'Servidor Rack 2U'),
       (28, 'Processador', '1x Intel® Xeon® Silver 4410Y'),
       (28, 'Memória', '1x 16GB, 4800MHz'),
       (28, 'Armazenamento', '1x 8TB HD SATA Hot Plug'),
       (28, 'Sistema Operacional', 'opcional'),
       (28, 'Assistência', '3 anos assistência básica');


INSERT INTO product_attribute (product_id, name, "value")
VALUES (29, 'Modelo', 'PowerEdge R660xs'),
       (29, 'Tipo', 'Servidor Rack 1U'),
       (29, 'Processador', 'Intel® Xeon® Silver 4410Y'),
       (29, 'Memória', '1x 16GB, 4800MHz'),
       (29, 'Armazenamento', '2x 480GB SSD SATA'),
       (29, 'Sistema Operacional', 'opcional'),
       (29, 'Assistência', ' 3 anos assistência básica');