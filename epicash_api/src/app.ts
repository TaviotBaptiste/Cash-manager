import express from 'express'
import cors from 'cors'
import * as swaggerUi from 'swagger-ui-express'
import { UsersController } from './resources/controllers/users.controller'
import { ProductsController } from './resources/controllers/products.controller'
import { ExceptionsHandler } from './middlewares/exceptions.handler'
import { UnknownRoutesHandler } from './middlewares/unknownRoutes.handler'
import * as fs from 'fs';
import 'dotenv/config'
import { connect } from "./config/db.config";

class App {
    public express: express.Application;

    /* Swagger files start */
    private db: any = {};
    private swaggerFile: any = (process.cwd()+"/src/swagger/swagger.json");
    private swaggerData: any = fs.readFileSync(this.swaggerFile, 'utf8');
    private customCss: any = fs.readFileSync((process.cwd()+"/src/swagger/swagger.css"), 'utf8');
    private swaggerDocument = JSON.parse(this.swaggerData);
    /* Swagger files end */

    constructor() {
        this.express = express();
        this.middleware();
        this.routes();

        this.db = connect();
        // For Development
        // { force: true, alter: true }
        this.db.sequelize.sync().then(() => {
            console.log("Drop and re-sync db.");
        });
    }

    // Configure Express middleware.
    private middleware(): void {

        this.express.use(express.json())
        this.express.use(cors())
    }

    private routes(): void {

        /**
         * Toutes les routes CRUD pour les animaux seronts préfixées par `/pets`
         */
        this.express.use('/api/users', UsersController)

        this.express.use('/api/products', ProductsController)


        // // swagger docs
        this.express.use('/', swaggerUi.serve,
            swaggerUi.setup(this.swaggerDocument, null, null, this.customCss));

        /**
         * Pour toutes les autres routes non définies, on retourne une erreur
         */
        this.express.all('*', UnknownRoutesHandler)

        /**
         * Gestion des erreurs
         * /!\ Cela doit être le dernier `app.use`
         */
        this.express.use(ExceptionsHandler)
    }
}

export default new App().express;