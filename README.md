# projeto1 

projeto de sistemas distribuidos senacrs


Faculdade de Tecnologia SENACRS
Tecnólogo em Análise e Desenvolvimento de Sistemas
Sistemas Distribuídos
Prof. Luis Henrique Ries
Especificação do Trabalho I – Comunicação

Este trabalho tem como objetivo realizar um sistema distribuído utilizando os conceitos
de comunicação e modelos para sistemas distribuídos. Para isso, uma aplicação
distribuída chamada MyHomeTop deve ser desenvolvida e documentada de maneira
adequada, aplicando os conhecimentos adquiridos na disciplina.
Instruções gerais:
Os alunos devem realizar um sistema que simule uma aplicação voltada para automação
residencial. Esse sistema consiste em uma aplicação distribuída que permite o
monitoramento e controle de sensores 1 (detectores de presença, medidores de
temperatura, etc) e atuadores 2 (interruptores de luz, sinalizador sonoro, etc) que
poderiam estar localizados em diferentes locais da casa (diferentes cômodos da casa). A
ideia é que esses sensores e atuadores inicialmente sejam simulados através de uma
aplicação Java Desktop.
Os sensores e atuadores poderão ser realizados por simulação através de aplicações Java
Desktop. Quando a aplicação é inicializada, ela deverá comunicar o sistema que novos
sensores e atuadores estão sendo instaladas no ambiente. Para isso, a aplicação
funcionará de forma semelhante a um cadastro de sensores e atuadores no ambiente. As
informações que deverão ser disponibilizadas no cadastro são:
•
Sensor: nome, descrição, tipo (temperatura, luz, etc), localização na casa
(cômodo). Esse dispositivo tem como objetivo informar ao sistema alguma
informação referente à sua finalidade. Por exemplo, no caso de um sensor de
temperatura, o sistema deve informar a temperatura do ambiente.
•
Atuador: nome, descrição, tipo (temperatura, luz, porta, janela, etc), localização
na casa (cômodo). Esse dispositivo tem como objetivo realizar o controle no
ambiente e para isso, esse dispositivo permite que o sistema ative/desative uma
ação e modifique alguma informação, referente à sua finalidade. Por exemplo,
no caso de um interruptor de luz, o sistema deve permitir que um usuário ligue
ou desligue a luz e altere a intensidade da mesma.
Após realizar o cadastro dos sensores e atuadores no sistema, a aplicação deverá abrir
uma nova janela referente a cada dispositivo para simular o funcionamento do mesmo
no ambiente. Por exemplo, numa aplicação simulando um sensor de temperatura, o
usuário poderá alterar a temperatura atual do ambiente. Outro exemplo, agora no caso
1
2
Sensor: http://pt.wikipedia.org/wiki/Sensor
Atuador: http://pt.wikipedia.org/wiki/Atuadorde um atuador para luz, a aplicação informaria se a luz está ligada ou desligada e a
intensidade da luz.
A realização do monitoramento e controle é realizada a partir de uma ou mais
aplicações de monitoramento denominada Monitor. Cada Monitor apresenta as
informações atualizadas dos sensores e permite o controle e atualização dos atuadores
da aplicação. Por exemplo, um Monitor poderia mostrar em tempo real os dados de um
sensor de temperatura (temperatura de um local) e permitiria abrir uma porta de
garagem em outro local.
O Monitor é uma aplicação Java Desktop que inicia a partir de uma tela de login.
Quando o usuário faz o login, as informações (nome do usuário e senha) são enviadas
ao sistema e este realiza a autenticação. Após o usuário estar logado, ele poderá
monitorar e controlar os sensores e atuadores do ambiente.
Para criar esse sistema, os alunos deverão se preocupar com a forma que o problema
será resolvido, sob a ótica de sistemas distribuídos. Assim, esse trabalho deverá ter a
preocupação para tratar tanto a funcionalidade quanto a comunicação. As soluções
relacionadas devem ser voltadas para tratar os desafios existentes da área de sistemas
distribuídos (heterogeneidade, tolerância a falhas, etc) ou outros quesitos importantes de
aplicações (desempenho, acoplamento, interoperabilidade, etc).
Além do sistema, um relatório deve ser realizado. Esse relatório deve ser sucinto e deve
conter os seguintes tópicos (um modelo é disponibilizado junto):
- Introdução: apresentação geral do trabalho (contextualização, problema, solução e
resultados);
- Problema: Descrição do MyHomeTop (negócio) e desafios encontrados no trabalho;
- Solução: Descrição da solução da aplicação e dos desafios tratados no trabalho.
- Considerações Finais: conclusão sobre as soluções, dificuldades encontradas, etc.
“Indo além”
Para o conceito A, o aluno poderá realizar um dos dois complementos para o trabalho:
1. Novos dispositivos na simulação: a aplicação poderia ser simulada por
dispositivos móveis (Android, iOS, etc) ou desenvolvida em plataformas
eletrônicas (Arduíno, Raspberry PI, etc)
2. Filtros de visualização: O Monitor poderia ter buscas e filtros para visualizar os
dispositivos de maneira mais eficiente. Por exemplo, poderia acrescentar filtros
de tipos para poder buscar os sensores de temperatura de toda casa.
O relatório deverá também contemplar o complemento escolhido. Assim, devem-se
acrescentar as informações sobre o sistema, os desafios a serem trabalhos e a solução
realizada para o complemento.Avaliação:
A avaliação do trabalho será realizada seguindo os seguintes critérios:
- Implementação: todas as funcionalidades realizadas e emprego correto dos modelos de
Sistema Distribuído;
- Relatório: clareza e consistência.
Para o conceito C, é necessária apenas a tarefa principal do trabalho: o monitoramento e
controle. Assim, não é necessário nem o cadastramento dos sensores/atuadores nem o
sistema de autenticação. No entanto, para testar adequadamente essa tarefa principal,
pelo menos 3 aplicações, cada uma com 2 sensores e 2 atuadores deverão estar
relacionadas no sistema. E para realizar o monitoramento, 2 monitores devem estar
disponibilizados.
Apresentação e Entrega:
O trabalho deverá ser realizado em grupo de até no máximo 3 (três) alunos. O(s)
aluno(s) deverá(rão) estar presente(s) em aula para apresentar o trabalho (para o
professor). A avaliação da apresentação será definida individualmente. A entrega do
trabalho deverá ser realizada pelo Moodle anexando um arquivo zipado contendo:
O DOC ou PDF contendo o relatório.
Código-fonte zipado
Data de Entrega/Apresentação: 27/10.
