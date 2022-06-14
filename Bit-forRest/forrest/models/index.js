//'use strict';

//const fs = require('fs');
//const path = require('path');
const Sequelize = require('sequelize');
const User = require('./user');
const Old = require('./old');
const Product = require('./product');
const Chat = require('./chat');
const ChatRoom = require('./chatRoom');
const ChatImg = require('./chatImg');

//const basename = path.basename(__filename);
const env = process.env.NODE_ENV || 'development';
//const config = require(__dirname + '/../config/config.json')[env];
const config = require('../config/config.json')[env];
const db = {};

//let sequelize;
//if (config.use_env_variable) {
//  sequelize = new Sequelize(process.env[config.use_env_variable], config);
//} else {
//  sequelize = new Sequelize(config.database, config.username, config.password, config);
//}
const sequelize = new Sequelize(config.database, config.username, config.password, config);

/*
fs
  .readdirSync(__dirname)
  .filter(file => {
    return (file.indexOf('.') !== 0) && (file !== basename) && (file.slice(-3) === '.js');
  })
  .forEach(file => {
    const model = require(path.join(__dirname, file))(sequelize, Sequelize.DataTypes);
    db[model.name] = model;
  });

Object.keys(db).forEach(modelName => {
  if (db[modelName].associate) {
    db[modelName].associate(db);
  }
});
*/

db.sequelize = sequelize;
//db.Sequelize = Sequelize;

db.User = User;
db.Old = Old;
db.Product= Product;
db.Chat = Chat;
db.ChatRoom = ChatRoom;
db.ChatImg = ChatImg;

User.init(sequelize);
Old.init(sequelize);
Product.init(sequelize);
Chat.init(sequelize);
ChatRoom.init(sequelize);
ChatImg.init(sequelize);

User.associate(db);
Old.associate(db);
Product.associate(db);
Chat.associate(db);
ChatRoom.associate(db);
ChatImg.associate(db);

module.exports = db;
