const Sequelize = require('sequelize');

module.exports = class Product extends Sequelize.Model{
    static init(sequelize){
        return super.init({
            prodNo : {
                type: Sequelize.INTEGER,
                primaryKey: true,
                allowNull: false,
                autoIncrement: true,
            },
            width : {
                type: Sequelize.INTEGER,
                allowNull: false,
            },
            length : {
                type: Sequelize.INTEGER,
                allowNull: false,
            },
            height : {
                type: Sequelize.INTEGER,
                allowNull: false,
            },
            userId : {
                type: Sequelize.STRING(30),
                allowNull: false,
            },
            prodCondition : {
                type: Sequelize.INTEGER,
                allowNull: false,
                defaultValue: '1',
            },
            prodName : {
                type: Sequelize.STRING(40),
                allowNull: false,
            },
            prodQuantity : {
                type: Sequelize.INTEGER,
                allowNull: false,
            },
            prodDetail : {
                type: Sequelize.STRING(600),
                allowNull: false,
            },
            isRental : {
                type: Sequelize.BOOLEAN,
                allowNull: false,
                defaultValue: false,
            },
            rentalCounting : {
                type: Sequelize.INTEGER,
            },
            rentalPrice : {
                type: Sequelize.INTEGER,
            },
            account : {
                type: Sequelize.STRING(30),
            },
            deposit : {
                type: Sequelize.INTEGER,
            },
            category : {
                type: Sequelize.STRING(10),
                allowNull: false,
            },
            returnAddress : {
                type: Sequelize.STRING(100),
                allowNull: false,
            },
            prodImg : {
                type: Sequelize.STRING(100),
                allowNull: false,
            },
            recentImg : {
                type: Sequelize.STRING(100),
            },
        }, {
            sequelize,
            timestamps: false,
            modelName: 'Product',
            tableName: 'product',
            paranoid: false,
            charset: 'utf8',
            collate: 'utf8_general_ci',    
        });

    }
    
    static associate(db) {
        db.Product.belongsTo(db.User, {foreignKey: 'userId', targetKey: 'userId'});
        db.Product.hasMany(db.ChatRoom, {foreignKey: 'prodNo', sourceKey: 'prodNo'});
        
    }
};