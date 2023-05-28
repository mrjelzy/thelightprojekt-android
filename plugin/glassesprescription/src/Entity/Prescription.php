<?php
// modules/yourmodule/src/Entity/Article.php

class Prescription extends ObjectModel {
    public $id_prescription;
    public $id_customer;
    public $id_attachment;
    public $sphere_left;
    public $sphere_right;
    public $cylinder_left;
    public $cylinder_right;
    public $axis_left;
    public $axis_right;
    public $pd_left;
    public $pd_right;

    /**
     * @see ObjectModel::$definition
     */
    public static $definition = array(
        'table' => 'prescriptions',
        'primary' => 'id_prescription',
        'multilang' => false,
        'fields' => [
            'id_customer' => ['type' => self::TYPE_INT, 'validate' => 'isUnsignedInt', 'required' => true],
            'id_attachment' => ['type' => self::TYPE_INT, 'validate' => 'isUnsignedInt', 'required' => false],
            'sphere_left' => ['type' => self::TYPE_FLOAT, 'validate' => 'isFloat', 'required' => false],
            'sphere_right' => ['type' => self::TYPE_FLOAT, 'validate' => 'isFloat', 'required' => false],
            'cylinder_left' => ['type' => self::TYPE_FLOAT, 'validate' => 'isFloat', 'required' => false],
            'cylinder_right' => ['type' => self::TYPE_FLOAT, 'validate' => 'isFloat', 'required' => false],
            'axis_left' => ['type' => self::TYPE_FLOAT, 'validate' => 'isFloat', 'required' => false],
            'axis_right' => ['type' => self::TYPE_FLOAT, 'validate' => 'isFloat', 'required' => false],
            'pd_left' => ['type' => self::TYPE_FLOAT, 'validate' => 'isFloat', 'required' => false],
            'pd_right' => ['type' => self::TYPE_FLOAT, 'validate' => 'isFloat', 'required' => false],
        ],
    );

    protected $webserviceParameters = array(
      'objectNodeName' => 'prescription',
      'objectsNodeName' => 'prescriptions',
      'fields' => array(
            'id_prescription' => array(),
            'id_customer' => array(),
            'id_attachment' => array(),
            'sphere_left' => array(),
            'sphere_right' => array(),
            'cylinder_left' => array(),
            'cylinder_right' => array(),
            'axis_left' => array(),
            'axis_right' => array(),
            'pd_left' => array(),
            'pd_right' => array()
        )
    );
}
