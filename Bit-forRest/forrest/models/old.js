const Sequelize = require('sequelize');

module.exports = class Old extends Sequelize.Model{
    static init(sequelize){
        return super.init({
            oldNo : {
                type: Sequelize.INTEGER,
                primaryKey: true,
                allowNull: false,
                autoIncrement: true,
            },
            userId : {
                allowNull:false,
                type: Sequelize.STRING(30),
            },
            oldPrice: {
                allowNull: false,
                type: Sequelize.INTEGER,
            },
            oldTitle:{
                type: Sequelize.STRING(100),
                allowNull: false,
            },
            oldDetail:{
                type: Sequelize.STRING(8000),
                allowNull: false,
            },
            oldDate:{
                type: Sequelize.DATE,
                allowNull: false,
                defaultValue: Sequelize.fn('NOW'),
            },
            oldView:{
                type: Sequelize.INTEGER,
                allowNull: false,
            },
            category:{
                type: Sequelize.STRING(10),
                allowNull: false,
            },
            oldState: {
                allowNull: false,
                type : Sequelize.BOOLEAN,
                defaultValue: true,
            },
            oldImg:{
                type: Sequelize.STRING(30),
                allowNull: false,
            }
        }, {
            sequelize,
            timestamps: false,
            modelName: 'Old',
            tableName: 'old',
            paranoid: false,
            charset: 'utf8',
            collate: 'utf8_general_ci',    
        });

    }
    
    static associate(db) {
        db.Old.belongsTo(db.User, {foreignKey: 'userId', targetKey: 'userId'});
        db.Old.hasMany(db.ChatRoom, {foreignKey: 'oldNo', targetKey: 'oldNo'});
        
    }
};