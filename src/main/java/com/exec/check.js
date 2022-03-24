var lrs = require("lrs");
var publicKeyList=JSON.parse(argv[1]);
console.log(lrs.verify(publicKeyList, argv[2], argv[3]));
