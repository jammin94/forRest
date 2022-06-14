const express = require('express');
const {QueryTypes} = require('sequelize');
const Query = require('../queries/query'); 
const db = require('../models/index');
//const Old = require('../models/old');

const router = express.Router();

router.get('/', async (req, res, next) => {
  try {

    //getOld
    let query = Query.getOld;
		let result = await db.sequelize.query(query, {
      replacements: {oldNo : '1'},
      type: QueryTypes.SELECT,
      raw: true
    });
    console.log(result);

    //listOldChatRoom
    query=Query.listOldChatRoom;
    result = await db.sequelize.query(query, {
      replacements: {userId : 'user01@naver.com'},
      type: QueryTypes.SELECT,
      raw: true
    });
    console.log(result);

    //listProductChatRoom
    query=Query.listProductChatRoom;
    result = await db.sequelize.query(query, {
      replacements: {userId : 'user01@naver.com'},
      type: QueryTypes.SELECT,
      raw: true
    });
    console.log(result);

  }catch (err) {
    console.error(err)
    next(err)
  }
});

module.exports = router;
