const Sequelize = require('sequelize');

module.exports = class ChatRoom extends Sequelize.Model{
    static init(sequelize){
        return super.init({
            chatRoomNo : {
                type: Sequelize.INTEGER,
                primaryKey: true,
                allowNull: false,
                autoIncrement: true,
            },
            oldNo : {
                allowNull:true,
                type: Sequelize.INTEGER,
            },
            prodNo : {
                allowNull:true,
                type: Sequelize.INTEGER,
            },
            inquireUserId:{
                type: Sequelize.STRING(30),
                allowNull : false,
            },
            ownerUserId:{
                type: Sequelize.STRING(30),
                allowNull : false,
            },
            
            inquireUserExit: {
                type: Sequelize.BOOLEAN,
                allowNull: false,
                defaultValue: false,
            },
            ownerUserExit: {
                type: Sequelize.BOOLEAN,
                allowNull: false,
                defaultValue: false,
            },
            createdAt: {
                type: Sequelize.DATE,
                defaultValue: Sequelize.fn('NOW'),
                allowNull: false,
                
              },
            
        }, {
            sequelize,
            timestamps: false,
            modelName: 'ChatRoom',
            tableName: 'chatRoom',
            paranoid: false,
            charset: 'utf8',
            collate: 'utf8_general_ci',    
        });

    }
    
    static associate(db) {
        db.ChatRoom.belongsTo(db.Old, {foreignKey: 'oldNo', targetKey: 'oldNo'});
        db.ChatRoom.belongsTo(db.Product, {foreignKey: 'prodNo', targetKey: 'prodNo'});
        db.ChatRoom.belongsTo(db.User, {foreignKey: 'inquireUserId', targetKey: 'userId', as: 'InquireUser'});
        db.ChatRoom.belongsTo(db.User, {foreignKey: 'ownerUserId', targetKey: 'userId', as: 'OwnerUser'});
        db.ChatRoom.hasMany(db.Chat, {foreignKey: 'chatRoomNo', sourceKey: 'chatRoomNo'});
        
    }
};