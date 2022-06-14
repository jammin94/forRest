const Sequelize = require('sequelize');

module.exports = class User extends Sequelize.Model{
    static init(sequelize){
        return super.init({
            userId : {
                type: Sequelize.STRING(30),
                primaryKey: true,
                allowNull: false,
            },
            nickname : {
                allowNull:false,
                type: Sequelize.STRING(30),
                unique: true,
            },
            phone:{
                type: Sequelize.STRING(20),
                allowNull: false,
                unique: true,
            },
            password: {
                allowNull: false,
                type: Sequelize.STRING(20),
            },
            userName: {
                allowNull: false,
                type: Sequelize.STRING(20),
            },
            userAddr: {
                allowNull: false,
                type: Sequelize.STRING(100),
            },
            role: {
                allowNull: false,
                type: Sequelize.STRING(10),
                defaultValue: 'user',
            },
            joinDate: {
                allowNull: false,
                type: Sequelize.DATE,
            },
            joinPath: {
                allowNull: false,
                type: Sequelize.STRING(10),
            },
            userImg: {
                type: Sequelize.STRING(100),
            },
            recentDate: {
                allowNull: false,
                type: Sequelize.DATE,
                defaultValue: Sequelize.fn('now'),
            },
            pushToken: {
                type: Sequelize.STRING(100),
            },
            leaveApplyDate: {
                type: Sequelize.DATE,
            },
            leaveDate: {
                type: Sequelize.DATE,
            },

        }, {
            sequelize,
            timestamps: false,
            modelName: 'User',
            tableName: 'user',
            paranoid: false,
            charset: 'utf8',
            collate: 'utf8_general_ci',    
        });

    }
    
    static associate(db) {
        db.User.hasMany(db.Old, {foreignKey: 'userId', sourceKey: 'userId'});
        db.User.hasMany(db.ChatRoom, {foreignKey: 'inquireUserId', sourceKey: 'userId', as: 'InquireUser'});
        db.User.hasMany(db.ChatRoom, {foreignKey: 'ownerUserId', sourceKey: 'userId', as: 'OwnerUser'});
        db.User.hasMany(db.Chat, {foreignKey: 'sendUserId', sourceKey: 'userId'});
    }
};