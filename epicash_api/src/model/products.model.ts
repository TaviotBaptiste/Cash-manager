import { Table, Column, DataType, Model, HasMany } from 'sequelize-typescript'

@Table
export class Products extends Model {
  
  @Column({
    allowNull: false,
    unique: true,
    type: DataType.TEXT,
  })
  libelle: string;
  
  @Column({
    allowNull: false,
    unique: false,
    type: DataType.FLOAT,
  })
  prix: number;

  @Column({
    allowNull: true,
    unique: false,
    type: DataType.TEXT,
  })
  desc: string;

  @Column({
    allowNull: true,
    unique: false,
    type: DataType.TEXT,
  })
  image: string;

  @Column({
    allowNull: false,
    unique: false,
    type: DataType.BOOLEAN,
  })
  activated: boolean;
  
} 

