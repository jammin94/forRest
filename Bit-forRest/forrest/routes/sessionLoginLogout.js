const express = require('express');

const router = express.Router();

//여러번 하면 overwrite. 로그인 시 이것도 같이 해줭
//:3001/sessionLoginLogout/login/:userId
router.get('/login/:userId', async (req, res, next) => {
  try {
   const sessionId=req.params.userId
   req.session.user= sessionId;
   req.session.save();
   console.log('sessionId 저장 : '+req.session.user);
   res.send(null);
  } catch (err) {
    console.error(err);
    next(err);
  }
});

//로그아웃 시 이것도 같이 해줭
//:3001/sessionLoginLogout/delete
router.get('/delete', async (req, res, next) => {
  try {
    if(req.session){
	console.log('sessionId: '+req.session.user);
        req.session.destroy();
    }
    else {
       	console.log('제거할 세션이 없습니다.');
    }
  } catch (err) {
    console.error(err);
    next(err);
  }
});


module.exports = router;
//sessionLoginLogout