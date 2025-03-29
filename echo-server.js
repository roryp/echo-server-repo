const readline = require('readline');

const rl = readline.createInterface({
  input: process.stdin,
  output: process.stdout,
  terminal: false
});

rl.on('line', (line) => {
  const request = JSON.parse(line);
  const response = {
    jsonrpc: "2.0",
    id: request.id,
    result: request.params.input  // Echo the input directly
  };
  console.log(JSON.stringify(response));
});
