const express = require('express');
const cors = require('cors');///////////////////
const path = require('path');
const morgan = require('morgan');
const nunjucks = require('nunjucks');
const cookieParser = require('cookie-parser');
const session = require('express-session');
const dotenv = require('dotenv');

dotenv.config();
const webSocket = require('./socket');

const { sequelize } = require('./models');

const oldChatRouter = require('./routes/oldChat');
const sessionLoginLogoutRouter = require('./routes/sessionLoginLogout');


const app = express();
app.set('port', process.env.PORT || 3001);
app.set('view engine', 'html');

nunjucks.configure('views', { //views에 있는 모든 파일들을 nunjucks 템플릿 처리한다.
  express: app,
  watch: true, //html이 변경될 때 마다 template 엔진을 다시 렌더링 한다
});

sequelize.sync({ force: false })
  .then(() => {
    console.log('데이터베이스 연결 성공');
  })
  .catch((err) => {
    console.error(err);
  });

  //세션 사용하자!
  
  const sessionMiddleware = session({
    resave: false, //false
    saveUninitialized: false,
    //secret: process.env.COOKIE_SECRET,
    secret: 'forrest',
    cookie: {
      httpOnly: true,
      secure: false, /*false*/
    },
  });
  
app.use(cors());///////////////////
app.use(morgan('dev'));
app.use(express.static(path.join(__dirname, 'public')));
app.use('/image', express.static(path.join(__dirname, 'uploads'))); // '/image'

app.use(express.json()); //parsing application을 위하여!
app.use(express.urlencoded({ extended: false })); // req.body parsing을 위하여!

//app.use(cookieParser(process.env.COOKIE_SECRET));
app.use(cookieParser('forrest'));
app.use(sessionMiddleware);


app.use('/oldChat', oldChatRouter);
app.use('/sessionLoginLogout', sessionLoginLogoutRouter);


//에러처리
app.use((req, res, next) => {
  const error =  new Error(`${req.method} ${req.url} 라우터가 없습니다.`);
  error.status = 404;
  next(error);
});

//에러처리
app.use((err, req, res, next) => {
  res.locals.message = err.message;
  res.locals.error = process.env.NODE_ENV !== 'production' ? err : {};
  res.status(err.status || 500);
  res.render('error');
});

const server = app.listen(app.get('port'), () => {
  console.log(app.get('port'), '번 포트에서 대기 중');
});

webSocket(server, app, sessionMiddleware);
