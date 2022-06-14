const Sequelize = require('sequelize');

module.exports = class Chat extends Sequelize.Model{
    static init(sequelize){
        return super.init({
            chatMessageNo : {
                type: Sequelize.INTEGER,
                primaryKey: true,
                allowNull: false,
                autoIncrement: true,
            },
            chatRoomNo : {
                allowNull:false,
                type: Sequelize.INTEGER,
            },
            sendUserId:{
                type: Sequelize.STRING(30),
                allowNull: false,
            },
            chatMessage: {
                type: Sequelize.STRING(4000),
                allowNull: false,
            },
            createdAt: {
                type: Sequelize.DATE,
                allowNull: false,
                defaultValue: Sequelize.fn('NOW'),
            },
            readOrNot: {
                type: Sequelize.BOOLEAN,
                allowNull: false,
                defaultValue: true,
            },
            
        }, {
            sequelize,
            timestamps: false,
            modelName: 'Chat',
            tableName: 'chat',
            paranoid: false,
            charset: 'utf8',
            collate: 'utf8_general_ci',    
        });

    }
    
    static associate(db) {
        db.Chat.belongsTo(db.ChatRoom, {foreignKey: 'chatRoomNo', targetKey: 'chatRoomNo'});
        db.Chat.belongsTo(db.User, {foreignKey: 'sendUserId', targetKey: 'userId'});
        db.Chat.hasMany(db.ChatImg, {foreignKey: 'chatMessageNo', sourceKey: 'chatMessageNo'});
        
        
    }
};