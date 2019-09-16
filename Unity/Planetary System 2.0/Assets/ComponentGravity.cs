using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class ComponentGravity : MonoBehaviour
{
    public float G = 1;

    // Start is called before the first frame update
    void Start()
    {
        
    }

    // Update is called once per frame
    void Update()
    {
        
    }

    void FixedUpdate() {
        var objects = GameObject.FindGameObjectsWithTag("Body");
        for (int i = 0; i < objects.Length; i++)
        {
            var x = objects[i];
            var xTransform = x.GetComponent<Transform>();
            var xRigid = x.GetComponent<Rigidbody>();
            for (int j = 0; j < objects.Length; j++)
            {
                if (i != j)
                {
                    var y = objects[j];
                    var yTransform = y.GetComponent<Transform>();
                    var yRigid = y.GetComponent<Rigidbody>();
                    var xToY = yTransform.position - xTransform.position;
                    var force = G * (xRigid.mass * yRigid.mass) / System.Math.Pow(xToY.magnitude, 2);
                    xRigid.AddForce(xToY.normalized * (float)force);
                }
            }
        }
    }
}
