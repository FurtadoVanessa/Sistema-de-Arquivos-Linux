<h2> Sistema-de-Arquivos-Linux </h2>

O sistema de arquivos foi um projeto realizado na disciplina de Sistemas Operacionais 2 2019 do curso Engenharia de Computação do IFSULDEMINAS - campus Poços de Caldas

O projeto consiste na simulação (via Java) do sistema de arquivos Linux, consistindo em i-nodes de mapeamento de blocos no HD, onde:

<b> Diretório </b>
<ul>
<li> Lista de Arquivos existentes no HD </li>
</ul>

<b> Arquivo </b>
<ul>
<li> Contém o nome e seu respectivo i-node </li>
</ul>

<b> I-node </b>
<ul>
<li> Possui os dados: Tamanho, Permissão de Escrita, Permissão de Leitura e Blocos Mapeados </li>
</ul>

<b> Blocos </b>
<ul>
<li> Possui o número de bloco e seu respectivo conteudo em um vetor de caracteres </li>
</ul>

<b> Blocos Livres </b>
<ul>
<li> Possui o número do bloco livre e a quantidade de blocos livres a sua frente </li>
</ul>

<b> HD </b>
<ul>
<li> Possui o tamanho do HD, o tamanho do bloco a ser dividido, a lista de blocos livres e a lista de blocos ocupados </li>
</ul>
