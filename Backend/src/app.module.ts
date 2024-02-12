import { Module } from '@nestjs/common';
import { AppController } from './app.controller';
import { AppService } from './app.service';
import { TypeOrmModule } from '@nestjs/typeorm';
import { ConfigModule, ConfigService } from '@nestjs/config';

import { UsersModule } from './users/users.module';
import { PostModule } from './post/post.module';
import ormConfig from './config/orm.config';
import ormConfigProd from './config/orm.config.prod';
import { User } from './users/entites/user.entity';
import { Post } from './post/entities/post.entity';
import { UserPostModule } from './user_post/user_post.module';
import { UserPost } from './user_post/entities/user_post.entity';
import { UserPermissionModule } from './user_permission/user_permission.module';
import { UserPermission } from './user_permission/entities/user_permission.entity';
import { AuthStrategyModule } from './auth_strategy/auth_strategy.module';
import { AuthStrategy } from './auth_strategy/entities/auth_strategy.entity';
import { MailerModule, TemplateAdapter } from '@nestjs-modules/mailer';
import { join } from 'path';
import {
  HandlebarsAdapter
} from "@nestjs-modules/mailer/dist/adapters/handlebars.adapter"

@Module({
  imports: [
    // ConfigModule.forRoot({
    //   isGlobal: true,
    //   load: [ormConfig],
    //   expandVariables: true,
    // }),
    // TypeOrmModule.forRootAsync({
    //   useFactory:
    //     process.env.NODE_ENV !== 'production' ? ormConfig : ormConfigProd,
    // }),
    TypeOrmModule.forRoot(
      {
        type: 'mysql',
        host: 'mysql-giathuan-giathuannguyen213-5e78.a.aivencloud.com',
        port: 18481,
        username: 'avnadmin',
        password: 'AVNS_6EEaBTyBsp2oVSODdA2',
        database: "defaultdb",
        entities: [
          Post,
          User,
          UserPost,
          UserPermission,
          AuthStrategy
        ],
        synchronize: true,
        autoLoadEntities: true,
        driver: {

        },
        migrations: ['dist/db/migrations/*.js'],


      }
    ),
    MailerModule.forRootAsync({
      imports: [
        ConfigModule
      ],
      inject:[
        ConfigService
      ],
      useFactory: async (config: ConfigService) => ({
        transport: {
          host: "smtp.gmail.com",
          port:587,
          secure: false,
          auth: {
            user: "giathuannguyen213@gmail.com",
            pass: "tqwdgwhyiauasigq"
          },
          tls: { rejectUnauthorized: false }
        },
        defaults: {
          from: "Test No Reply Email "
        },
        template: {
          dir: join(__dirname, "src/templates/email"),
          adapter: new HandlebarsAdapter(),
          options: {
            strict: true
          }
        }
      })
    }), 
    
    UsersModule,
    PostModule,
    UserPostModule,
    UserPermissionModule,
    AuthStrategyModule,
  ],
  controllers: [AppController],
  providers: [AppService],
})
export class AppModule { }
