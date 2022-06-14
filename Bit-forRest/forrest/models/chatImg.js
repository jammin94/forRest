const Sequelize = require('sequelize');

module.exports = class ChatImg extends Sequelize.Model{
    static init(sequelize){
        return super.init({
            chatImgNo : {
                type: Sequelize.INTEGER,
                primaryKey: true,
                allowNull: false,
                autoIncrement: true,
            },
            chatMessageNo : {
                allowNull:false,
                type: Sequelize.INTEGER,
            },
            fileName: {
                type: Sequelize.STRING(1000),
                allowNull: false,
            }
        }, {
            sequelize,
            timestamps: false,
            modelName: 'ChatImg',
            tableName: 'chatImg',
            paranoid: false,
            charset: 'utf8',
            collate: 'utf8_general_ci',    
        });

    }
    
    static associate(db) {
        db.ChatImg.belongsTo(db.Chat, {foreignKey: 'chatMessageNo', target: 'chatMessageNo'});
        
    }
};