
var lrs = require("lrs");
var big = require("big-integer");


// General Type to be used
// var publicKeyList=[];
// var message='';
// var secretKey={'publicKey':'', 'privateKey':''};


var secretKey=lrs.gen();
var publicKeyList=[lrs.gen().publicKey, lrs.gen().publicKey, lrs.gen().publicKey, secretKey.publicKey];
var message='';

var signed = lrs.sign(publicKeyList, secretKey, message);

console.log(signed, lrs.verify(publicKeyList, signed, message));
