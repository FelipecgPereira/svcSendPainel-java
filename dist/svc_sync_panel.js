const { exec } = require('child_process');

// Comando para executar o JAR
const comando = //'java -jar caminho/para/seu/arquivo.jar';

    'java -jar svcSendPanel.jar 192.168.1.222 5200 "https://editor.mobilibus.com/web/get-proximas-partidas/54byr/386641"';

// Executando o JAR como um processo filho
exec(comando, (erro, stdout, stderr) => {
    if (erro) {
        console.error(`Erro ao executar o JAR: ${erro.message}`);
        return;
    }
    console.log('Saída padrão do JAR:');
    console.log(stdout);
    console.error('Saída de erro do JAR:');
    console.error(stderr);
});